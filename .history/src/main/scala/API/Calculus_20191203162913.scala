package API

import scala.annotation.tailrec
import API.Complex
class Calculus {
  def pixelToComplex (pixelX:Double,pixelY:Double, width:Double, height:Double, minX:Double, maxX:Double, minY:Double, maxY:Double): Complex ={
    val xPercent:Double = pixelX / width
    val yPercent:Double = pixelY / height
    val cX:Double = minX + (maxX - minX) * xPercent
    val cY:Double = minY + (maxY - minY) * yPercent
    new Complex(cX,cY)
  }
  def pixelToComplex2 (pixelX:Double,pixelY:Double, width:Double, height:Double, minX:Double, maxX:Double, minY:Double, maxY:Double): Complex ={
    val xPercent:Double = pixelX / width
    val yPercent:Double = pixelY / height
    val cX:Double = minX + (maxX - minX) * xPercent
    val cY:Double = minY + (maxY - minY) * yPercent
    new Complex(minX + (pixelX/width) * (maxX - minX),minY + (pixelY/height)-(maxY - minY))
  }

  //Convert a value X to a new value Y in range Ymin-Ymax
  def convertRange(X:Int,oldMax:Int, Ymin:Int, Ymax:Int): Int ={
    ((X * (Ymax - Ymin)) / oldMax) + Ymin
  }



  //Return thenumber of iteration before the complex differ 
  def isMandelbrot(C: Complex,iter:Int): Int = {
    var Z = C
    for (x <- 0 to iter) {
      Z = Z.square.sum(C)
      if (Z.getReal > 2 | Z.getImaginary > 2) return x
    }
    iter
  }

    @tailrec
    final def rMandel(start:Int,z: Complex,c:Complex,iter:Int):Int =
      if ((start>=iter) | (z.getReal > 2.0 | z.getImaginary > 2.0) )  start else rMandel(start+1,z.square.sum(c),c,iter)

  def isMandelbrotRecursiv2(C: Complex,iter:Int,complex:Complex = new Complex(-3/4,0)): Int = {
    @tailrec
    def rMandel(start:Int,Z: Complex):Int =
      if ((start>=iter) | (Z.getReal > 2.0 | Z.getImaginary > 2.0) )  start else rMandel(start+1,Z.square.sum(complex))

    rMandel(0,C)
  }
}
