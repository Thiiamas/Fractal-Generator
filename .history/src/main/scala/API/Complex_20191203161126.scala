package API

class Complex(R:Double, i:Double) {
def getReal : Double = R
def getImaginary : Double = i

  //square a complex number
  def square(): Complex = {
    val realResult = (this.getReal * this.getReal) - (this.getImaginary * this.getImaginary)
     val imaginaryResult = 2 * this.getReal *this.getImaginary
     new Complex(realResult, imaginaryResult)
   }
}
