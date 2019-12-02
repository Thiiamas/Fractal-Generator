package API

import 
class Dimensions(x:(Double,Double),y:(Double,Double)) {
  def getX:(Double,Double) = x
  def getY:(Double,Double) = y
  def XX:(Double) = x._1.abs
}
