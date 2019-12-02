package API

import scala.annotation.tailrec

class Calculus {
  def pixelToComplex (pixelX:Double,pixelY:Double, width:Double, height:Double, minX:Double, maxX:Double, minY:Double, maxY:Double): Complex ={
    val xPercent:Double = pixelX / width
    val yPercent:Double = pixelY / height
    val cX:Double = minX + (maxX - minX) * xPercent
    val cY:Double = minY + (maxY - minY) * yPercent
    new Complex(cX,cY)
  }

  //square a complex number
  def square(complex: Complex): Complex = {
   val realResult = (complex.getReal * complex.getReal) - (complex.getImaginary * complex.getImaginary)
    val imaginaryResult = 2 * complex.getReal *complex.getImaginary
    new Complex(realResult, imaginaryResult)
  }

  //Convert a value X to a new value Y in range Ymin-Ymax
  def convertRange(X:Int,oldMax:Int, Ymin:Int, Ymax:Int): Int ={
    ((X * (Ymax - Ymin)) / oldMax) + Ymin
  }

  //Sum 2 Complex Number
  def Sum (first: Complex, second : Complex): Complex = {
    val realResult = first.getReal + second.getReal
    val imaginaryResult = first.getImaginary + second.getImaginary
    new Complex(realResult, imaginaryResult)
  }

  def isMandelbrot(C: Complex,iter:Int): Int = {
    var Z = C
    for (x <- 0 to iter) {
      Z = Sum(square(Z),C)
      if (Z.getReal > 2 | Z.getImaginary > 2) return x
    }
    iter
  }

  def isMandelbrotRecursiv(C: Complex,iter:Int): Int = {
    @tailrec
    def rMandel(start:Int,Z: Complex):Int =
      if ((start>=iter) | (Z.getReal > 2 | Z.getImaginary > 2) )  start else rMandel(start+1,Sum(square(Z),C))

    rMandel(0,C)
  }
}
