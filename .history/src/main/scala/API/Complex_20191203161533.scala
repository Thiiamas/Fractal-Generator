package API

class Complex(R:Double, i:Double) {
def getReal : Double = R
def getImaginary : Double = i

  //square this complex number
  def square(): Complex = {
    val realResult = (this.getReal * this.getReal) - (this.getImaginary * this.getImaginary)
     val imaginaryResult = 2 * this.getReal *this.getImaginary
     new Complex(realResult, imaginaryResult)
   }

  //Add a Complex Number
  def sum (toAdd : Complex): Complex = {
    val realResult:Double = this.getReal + toAdd.getReal
    val imaginaryResult:Double = this.getImaginary + toAdd.getImaginary
    new Complex(realResult, imaginaryResult)
  }
}
