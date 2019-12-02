import java.beans.EventHandler

import API.Dimensions
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, Pane}
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Slider, TextField, ToolBar}
import scalafx.scene.input.{DragEvent, KeyCode, KeyEvent, MouseEvent}

/*
object ScalaFXHelloWorld extends JFXApp {

  def   initCanvas(size:(Int,Int)):Canvas = {
    val painter: Painter = new Painter(size)
    painter.canvas
  }
  def  changeCanvasMan(size:(Int,Int),dim:Dimensions,iter:Int):Canvas  = {
    println("début")
    val painter:Painter = new Painter(size)
    painter.display("Mandelbrot",dim,iter)
    println("Fin")
    painter.canvas

  }
  //variables
  var size = (800,800)
  var dimensions = new Dimensions((-2,2),(-1.5,1.5))
  var vCanvas = initCanvas(size)
  var PrimaryScene: Scene = new Scene
  var ButtonScene : Scene = new Scene
  var iteration: Int = 25

  var MainStage:JFXApp.PrimaryStage = new JFXApp.PrimaryStage{
    title = "Fractal-Generator"
    scene = PrimaryScene
    height = size._1 + 50
    width = size._2
  }

  val mainPane = new Pane {
    children = vCanvas
    //onMouseClicked = (e: MouseEvent) => {
     // println(e)
    //}
  }

  //Button to draw
  val buttonTest = new Button("DrawnMandelbrot")
  buttonTest.onAction = (event: ActionEvent) => {
    mainPane.children.remove(vCanvas)
    vCanvas = changeCanvasMan(size,dimensions,slider.getValue.toInt)
    mainPane.children.add(vCanvas)
  }
  //TextField
  val iterSelector = new TextField()
  iterSelector.setText(iteration.toString)
  iterSelector.onKeyPressed= (event : KeyEvent) =>{
    if (event.code == KeyCode.Enter) print("HAHAHAHA")
  }
  //Slider
  val slider = new Slider(10,100,iteration)

  val toolBar = new ToolBar();
  toolBar.items.addAll(buttonTest,iterSelector,slider)
  mainPane.children.add(toolBar)
  PrimaryScene.root = mainPane
}*/
