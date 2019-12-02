import java.awt.Polygon

import API.Complex
import API.Dimensions
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._


class Mandelbrot(size:(Int,Int)) {
  import java.awt.Color
  import java.awt.geom._
  import java.awt.image.BufferedImage

  import API.Calculus

  import scala.math.sqrt

  val calculus = new Calculus

  // create an image
  val canvas = new Canvas(size._1, size._2)
  // get Graphics2D for the image
  val g = canvas.graphicsContext2D

  // clear background
  g.fill = Green
  g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)
  //DrawMandelbrot((-2,2),(-1.5,1.5))
  def init(dim:Dimensions,iteration:Int): Unit = {
    DrawMandelbrot(dim.getX,dim.getY,iteration)
  }
  def display(name:String,dim:Dimensions,iteration:Int,complex:Complex = new Complex(-3/4,0)): Unit = {
    name match {
      case "Mandelbrot" => {
        //Draw Mandelbrot
        val tX = (-0.562238-2.02837e-006 ,-0.562238+2.02837e-006)
        val tY = (-0.642828-2.02837e-006,-0.642828+0.272479)
        DrawMandelbrot(dim.getX,dim.getY,iteration)
        //DrawMandelbrot(dim.getX,dim.getY,iteration)
      }
      case "MandelbrotComplex" => {
        DrawMandelbrotFromPoint(dim.getX,dim.getY,iteration,complex)

      }
    }
  }

  //Prototype not used in current code for a zoom with java swing
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

  //Draw the mandelbrotSet and color depending on divergence
  def DrawFractal(pX:(Double,Double), pY:(Double,Double),iteration:Int,complex:Complex): Unit ={
    // clear background
    g.fill = White
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)
    for (x <- 0 to size._1) {
      for (y <- 0 to size._2){
        val point = calculus.pixelToComplex(x,y,size._1,size._2,pX._1,pX._2,pY._1,pY._2)
        val iter = calculus.isMandelbrotRecursiv2(point,iteration,complex)

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
    for (x <- 0 to size._1) {
      for (y <- 0 to size._2){
        val point = calculus.pixelToComplex(x,y,size._1,size._2,pX._1,pX._2,pY._1,pY._2)
        val iter = calculus.isMandelbrotRecursiv(point,iteration)

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

  //Draw a triangle shaped fractal


  def getCanvas:Canvas = canvas
  def getContext:GraphicsContext = g
}

