import java.awt.Polygon

import API.Complex
import API.Dimensions
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.paint.Color._


class Mandelbrot(size:(Int,Int)) {
  import java.awt.Color
  import java.awt.geom._
  import java.awt.image.BufferedImage

  import API.Calculus

  import scala.math.sqrt

  val calculus = new Calculus

  // create an image
  val canvas = new Canvas(size._1, size._2/1.2)
  // get Graphics2D for the image
  val g = canvas.graphicsContext2D
  var vName:String = "Man"

  // clear background
  g.fill = Green
  g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

  def init(dim:Dimensions,iteration:Int): Unit = {
    DrawMandelbrot(dim.getX,dim.getY,iteration)
  }

  //Function called to draw various fractals
  def display(name:String,dim:Dimensions,iteration:Int,complex:Complex = new Complex(-3/4,0)): Unit = {
    name match {
      case "Mandelbrot" => {
        //Draw Mandelbrot
        val tX = (-0.562238-2.02837e-006 ,-0.562238+2.02837e-006)
        val tY = (-0.642828-2.02837e-006,-0.642828+0.272479)
        DrawMandelbrot(dim.getX,dim.getY,iteration)
        vName = "Man"
        //DrawMandelbrot(dim.getX,dim.getY,iteration)
      }
      case "Fractal" => {
        DrawFractal(dim.getX,dim.getY,iteration,complex)
        vName = "Other"
      }
    }
  }

  //Prototype not used in current code for a zoom with java swing, kept for records
  def zoomMandelbrot(start:(Int,Int),end:(Int,Int),dimX:(Double,Double),dimY:(Double,Double)): Unit = {
    // clear background
    g.fill = White
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)
    val complexStart = calculus.pixelToComplex(start._1,start._2,size._2,size._1,dimX._1,dimX._2,dimY._1,dimY._2)
    val complexEnd = calculus.pixelToComplex(end._1,end._2,size._1,size._2,dimX._1,dimX._2,dimY._1,dimY._2)
    //dimX = (min(complexStart.getReal,complexEnd.getReal),max(complexStart.getReal,complexEnd.getReal))
    //dimY = (min(complexEnd.getImaginary,complexEnd.getImaginary),max(complexEnd.getImaginary,complexEnd.getImaginary))
    val finalDimX = (complexStart.getReal,complexEnd.getReal)
    val finalDimY = (complexStart.getImaginary,complexEnd.getImaginary)
    DrawMandelbrot(finalDimX,finalDimY,25)
    //g.dispose()
  }

  //Draw a fractal depending on the complex number "complex" and color it depending on divergence
  //Work the same way as the DrawMandelbrot basicly, the only difference is at the differ calculation
  def DrawFractal(pX:(Double,Double), pY:(Double,Double),iteration:Int,complex:Complex): Unit ={
    // clear background
    g.fill = White
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)
    for (x <- 0 to size._1) {
      for (y <- 0 to size._2){
        val point = calculus.pixelToComplex(x,y,size._1,size._2,pX._1,pX._2,pY._1,pY._2)
        val iter = calculus.rMandel(0,point,complex,iteration)

        //Colorisation
        val maxRed = 196
        val maxGreen = 90
        val maxBlue = 90
        val iterRangeRed = calculus.convertRange(iter,iteration,0,maxRed)
        val iterRangeGreen = calculus.convertRange(iter,iteration,0,maxGreen)
        val iterRangeBlue = calculus.convertRange(iter,iteration,0,maxBlue)
        val fxColor:scalafx.scene.paint.Color = scalafx.scene.paint.Color.rgb(maxRed - iterRangeRed,maxBlue - iterRangeGreen,maxBlue - iterRangeBlue)

        g.fill = fxColor
        g.fillRect(x,y,1,1)
      }
    }
  }

  //Draw the mandelbrotSet and color depending on divergence
  def DrawMandelbrot(pX:(Double,Double), pY:(Double,Double),iteration:Int): Unit ={
    // clear background
    g.fill = White
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)
    //For each canvas's pixel
    for (x <- 0 to size._1;
         y <- 0 to size._2)
    {
      //Convert a pixel to a complex number
      val point = calculus.pixelToComplex(x,y,size._1,size._2,pX._1,pX._2,pY._1,pY._2)
      //Calcul how fast it differ, with a limit of "iteration" number of time we iterate
      val iter = calculus.rMandel(0,point,point,iteration)

      //Colorisation using rgb value, might be a bit odd but it's one of the only way i found
      val maxRed = 196
      val maxGreen = 90
      val maxBlue = 90
      //convert the number of iteration it took to differ to calculate a rgb value
      val iterRangeRed = calculus.convertRange(iter,iteration,0,maxRed)
      val iterRangeGreen = calculus.convertRange(iter,iteration,0,maxGreen)
      val iterRangeBlue = calculus.convertRange(iter,iteration,0,maxBlue)
      val fxColor:scalafx.scene.paint.Color = scalafx.scene.paint.Color.rgb(maxRed - iterRangeRed,maxBlue - iterRangeGreen,maxBlue - iterRangeBlue)

      g.fill = fxColor
      g.fillRect(x,y,1,1)
    }
  }

  //Getters
  def getCanvas:Canvas = canvas
  def getContext:GraphicsContext = g
}

