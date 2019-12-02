import java.awt.Polygon

import API.Complex
import API.Dimensions
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._


class Painter(size:(Int,Int)) {
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
  def display(name:String,dim:Dimensions,iteration:Int): Unit = {
    name match {
      case "Mandelbrot" => {
        //Draw Mandelbrot
        val tX = (-0.562238-2.02837e-006 ,-0.562238+2.02837e-006)
        val tY = (-0.642828-2.02837e-006,-0.642828+0.272479)
        DrawMandelbrot(dim.getX,dim.getY,iteration)
        //DrawMandelbrot(dim.getX,dim.getY,iteration)
      }
      case "Triangle" => {
        //Draw Triangle
        val vx = Array(800.0,800.0,200.0)
        val vy = Array(200.0,800.0,500.0)
        DrawnTriangle(vx,vy)
      }
    }
  }

  //Faudrais que ça renvoie un canvas ou truc du genre
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
  def DrawMandelbrotFromPoint(pX:(Double,Double), pY:(Double,Double),iteration:Int,complex:Complex): Unit ={
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
  def DrawnTriangle(p1 : Array[Double] , p2 :Array[Double] ): Unit={
    // clear background
    g.fill = White
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    val gap = sqrt( (p1( 0 ) - p1( 1 )) * (p1( 0 ) - p1( 1 )) + (p2( 0 ) - p2( 1 )) * (p2( 0 ) - p2( 1 )) )
    print(gap)
    g.fill = Black

    if (gap < 10) g.fillPolygon(p1, p2, 3)
    else {
      val xab = (p1( 0 ) + p1( 1 )) / 2
      val xac = (p1( 0 ) + p1( 2 )) / 2
      val xbc = (p1( 1 ) + p1( 2 )) / 2

      val yab = (p2( 0 ) + p2( 1 )) / 2
      val yac = (p2( 0 ) + p2( 2 )) / 2
      val ybc = (p2( 1 ) + p2( 2 )) / 2
      val x1 = Array( p1( 0 ), xab, xac )
      val y1 = Array( p2( 0 ), yab, yac )
      DrawnTriangle( x1, y1 )
      val x2 = Array( p1( 1 ), xab, xbc )
      val y2 = Array( p2( 1 ), yab, ybc )
      DrawnTriangle( x2, y2 )
      val x3 = Array( p1( 2 ), xac, xbc )
      val y3 = Array( p2( 2 ), yac, ybc )
      DrawnTriangle(x3,y3)
    }
  }

  def getCanvas:Canvas = canvas
  def getContext:GraphicsContext = g
}

