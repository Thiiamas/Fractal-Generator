import java.awt.Polygon

import API.Complex
import API.Calculus
import API.Dimensions
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scala.math.sqrt


class Polygons(size:(Int,Int)){
    val calculus = new Calculus

    // create an image
    val canvas = new Canvas(size._1, size._2)
    // get Graphics2D for the image
    val g = canvas.graphicsContext2D
  
    // clear background
    g.fill = Green
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    def getCanvas:Canvas = canvas
    def getContext:GraphicsContext = g

    def display(name:String): Unit = {
        name match {
            case "Triangles" => {
                //Draw Triangle
                val vx = Array(800.0,800.0,200.0)
                val vy = Array(200.0,800.0,500.0)
                DrawnTriangle(vx,vy)
            }
        }
    }

    def DrawnTriangle(p1 : Array[Double] , p2 :Array[Double] ): Unit={
        // clear background
        g.fill = Green
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

}