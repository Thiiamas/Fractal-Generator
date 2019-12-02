import java.beans.EventHandler

import API.Dimensions
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, Pane}
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Slider, TextField, ToolBar}
import scalafx.scene.input.{DragEvent, KeyEvent, MouseEvent}
import scalafx.scene.shape.StrokeLineCap.Butt
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color._
import scalafx.scene.input.ScrollEvent
import API.Calculus

object ScalaFXHelloWorld extends JFXApp {
  val calculus = new Calculus()
  val dim = (750,750)
  var dimension = new Dimensions((-2,1.5),(-1.5,1.5))
  var PrimaryScene: Scene = new Scene
  var ButtonScene : Scene = new Scene
  var iteration: Int = 25
  var MainStage:JFXApp.PrimaryStage = new JFXApp.PrimaryStage{
    title = "Fractal-Generator"
    scene = PrimaryScene
    height = dim._1
    width = dim._2
  }

  val painter = new Painter(dim)  //initialiser directement
  val mainPane = new Pane {
    children = painter.getCanvas
  }
  //Button to reset
  val reset = new Button("Reset")
  reset.onAction = (event: ActionEvent) => {
    dimension = new Dimensions((-2,1.5),(-1.5,1.5))
    painter.init(dimension,slider.getValue().toInt)
  }
  //Button to draw
  val buttonTest = new Button("DrawnMandelbrot")
  buttonTest.onAction = (event: ActionEvent) => {
    println("début")
    painter.display("Mandelbrot",dimension,slider.getValue.toInt)
    println("Fin")
  }
  //Slider
  val slider = new Slider(10,110,iteration)
  var savedSlider = slider.getValue()
  var lastTime = 0L
  val timer = AnimationTimer(t => {
    if (lastTime>0){
      val delta = (t-lastTime)/1e9
    }
    if (savedSlider != slider.getValue) {
      savedSlider = slider.getValue()
      painter.display("Mandelbrot", dimension, savedSlider.toInt)
    }
    mainPane.onScroll = (event: ScrollEvent) => {
      //si deltaY > 0 on zoom
      val zoomFactor = 1.5
      val point = (event.x,event.y)
      val complexPoint = calculus.pixelToComplex(point._1,point._2,dim._1,dim._2,dimension.getX._1,dimension.getX._2,dimension.getY._1,dimension.getY._2)
      print(complexPoint.getReal,complexPoint.getImaginary)

      println(event)
      if (event.deltaY > 0 ) {
        val currentScaleX = dimension.XX/zoomFactor
        val currentScaleY = dimension.YY/zoomFactor
        val newX = (complexPoint.getReal - currentScaleX/2,complexPoint.getReal + currentScaleX/2)
        val newY = (complexPoint.getImaginary - currentScaleY/2,complexPoint.getImaginary + currentScaleY/2)
        //dimension = new Dimensions(newX,newY)
        painter.display("Mandelbrot",new Dimensions(newX,newY),slider.getValue().toInt)
      }
      //sinon on dezoom
      else if (event.deltaY < 0) {
        val currentScaleX = dimension.XX*zoomFactor
        val currentScaleY = dimension.YY*zoomFactor
        val newX = (complexPoint.getReal - currentScaleX/2,complexPoint.getReal + currentScaleX/2)
        val newY = (complexPoint.getImaginary - currentScaleY/2,complexPoint.getImaginary + currentScaleY/2)
        dimension = new Dimensions((dimension.getX._1*zoomFactor,dimension.getX._2*zoomFactor),(dimension.getY._1*zoomFactor,dimension.getY._2*zoomFactor))
        painter.display("Mandelbrot",dimension,slider.getValue().toInt)
      }
    }
    lastTime = t
  })
  val Repeat = new Button("Repeat")
  Repeat.onAction = (event: ActionEvent) => {
    timer.start()
  }
  val Stop = new Button("Stop")
  Stop.onAction = (event: ActionEvent) => {
    timer.stop()
  }

  val toolBar = new ToolBar();
  toolBar.items.addAll(reset,buttonTest,Repeat,Stop,slider)
  println("Vers la fin")
  mainPane.children.add(toolBar)
  PrimaryScene.root = mainPane
}