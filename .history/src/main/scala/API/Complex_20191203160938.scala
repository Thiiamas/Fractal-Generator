package API

class Complex(R:Double, i:Double) {
def getReal : Double = R
def getImaginary : Double = i

  //square a complex number
  def square(complex: Complex): Complex = {
    val realResult = (complex.getReal * complex.getReal) - (complex.getImaginary * complex.getImaginary)
     val imaginaryResult = 2 * complex.getReal *complex.getImaginary
     new Complex(realResult, imaginaryResult)
   }
}
