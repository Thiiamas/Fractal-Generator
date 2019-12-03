import java.awt.Polygon

import API.Complex
import API.Calculus
import API.Dimensions
import javafx.scene.shape.Line
import scalafx.print.PrintColor
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._

import scala.math.sqrt


class Polygons(size:(Int,Int)){
    val calculus = new Calculus
    var k =0
    // create an image
    val canvas = new Canvas(size._1, size._2)
    // get Graphics2D for the image
    val g = canvas.graphicsContext2D
  
    // clear background
    g.fill = White
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    def getCanvas:Canvas = canvas
    def getContext:GraphicsContext = g

    def display(name:String): Unit = {
        name match {
            case "Triangles" => {
                //Draw Triangle 
                val vx = Array(0.1*size._1,0.9*size._1,0.5*size._1)
                val vy = Array(0.9*size._2,0.9*size._2,0.1*size._2)
                drawnTriangle(vx,vy)

            }
        }
    }
    //recusive function
    def drawnTriangle(p1 : Array[Double] , p2 :Array[Double] ): Unit={

        //gap between 2 points of a triangle
        val gap = sqrt( (p1( 0 ) - p1( 1 )) * (p1( 0 ) - p1( 1 )) + (p2( 0 ) - p2( 1 )) * (p2( 0 ) - p2( 1 )) )
        //print(gap)

        g.fill = Black
        //when the gap is too small it stops drawing triangle
        if (gap < size._1*0.001){


            g.fillPolygon(p1, p2, 3)

        }
        else {
            //it creates the new vertices of the new triangles in the middle of each edges
            val xab = (p1( 0 ) + p1( 1 )) / 2
            val xac = (p1( 0 ) + p1( 2 )) / 2
            val xbc = (p1( 1 ) + p1( 2 )) / 2

            val yab = (p2( 0 ) + p2( 1 )) / 2
            val yac = (p2( 0 ) + p2( 2 )) / 2
            val ybc = (p2( 1 ) + p2( 2 )) / 2
            //it draws the new triangles
            val x1 = Array( p1( 0 ), xab, xac )
            val y1 = Array( p2( 0 ), yab, yac )
            drawnTriangle( x1, y1 )
            val x2 = Array( p1( 1 ), xab, xbc )
            val y2 = Array( p2( 1 ), yab, ybc )
            drawnTriangle( x2, y2 )
            val x3 = Array( p1( 2 ), xac, xbc )
            val y3 = Array( p2( 2 ), yac, ybc )
            drawnTriangle(x3,y3)
        }
    }
}