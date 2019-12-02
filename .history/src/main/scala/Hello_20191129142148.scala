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

object ScalaFXHelloWorld extends JFXApp {
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
      var currentScale = dimension.XX
      println(event)
/*       if (event.deltaY > 0 ) {
        dimension = new Dimensions((dimension.getX._1/zoomFactor,dimension.getX._2/zoomFactor),(dimension.getY._1/zoomFactor,dimension.getY._2/zoomFactor))
        painter.display("Mandelbrot",dimension,slider.getValue().toInt)
      }
      //sinon on dezoom
      else if (event.deltaY < 0) {
        dimension = new Dimensions((dimension.getX._1*zoomFactor,dimension.getX._2*zoomFactor),(dimension.getY._1*zoomFactor,dimension.getY._2*zoomFactor))
        painter.display("Mandelbrot",dimension,slider.getValue().toInt)
      } */
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
  toolBar.items.addAll(buttonTest,Repeat,Stop,slider)
  println("Vers la fin")
  mainPane.children.add(toolBar)
  PrimaryScene.root = mainPane
}