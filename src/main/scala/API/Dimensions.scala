package API

class Dimensions(x:(Double,Double),y:(Double,Double)) {
  def getX:(Double,Double) = x
  def getY:(Double,Double) = y
  def XX:(Double) = x._1.abs + x._2.abs
  def YY:Double = y._1.abs + y._2.abs
}
