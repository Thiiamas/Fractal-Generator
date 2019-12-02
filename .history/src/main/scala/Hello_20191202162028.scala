import java.beans.EventHandler

import API.Dimensions
import API.Complex
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
import javafx.scene.layout.TilePane

object ScalaFXHelloWorld extends JFXApp {
  val calculus = new Calculus()
  val dim = (750,750)
  var dimension = new Dimensions((-2,1),(-1.5,1.5))
  //for triangles
  val poly = new Polygons(dim)
  val mandelbrotScene: Scene = new Scene
  val triangleScene:Scene = new Scene {
    val draw = new Button("Draw")
    draw.onAction = (event:ActionEvent) => {
      poly.display("Triangles")
    }
    root = new Pane {
      content = poly.getCanvas
    }
    content = draw
  }
  var iteration: Int = 25
  //Scene for startup
  val startScene = new Scene {
    fill = LightBlue
    val mandelbrot = new Button("Mandelbrot")
    mandelbrot.onAction = (event:ActionEvent) => {
      MainStage.scene = mandelbrotScene
    }
    val triangle = new Button("Triangle")
    triangle.onAction = (event:ActionEvent) => {
      MainStage.scene = triangleScene
    }
    content = new TilePane (mandelbrot,triangle)
  }
  var MainStage:JFXApp.PrimaryStage = new JFXApp.PrimaryStage{
    title = "Fractal-Generator"
    scene = startScene
    height = dim._1
    width = dim._2
  }

  val mandelbrot = new Mandelbrot(dim)  //initialiser directement
  val mainPane = new Pane {
    //mandelbrot.display("Mandelbrot",dimension,slider.getValue.toInt)
    children = mandelbrot.getCanvas
  }
  //Button to reset
  val reset = new Button("Reset")
  reset.onAction = (event: ActionEvent) => {
    dimension = new Dimensions((-2,2),(-2,2))
    mandelbrot.init(dimension,slider.getValue().toInt)
  }
  //Button to draw
  val buttonTest = new Button("DrawnMandelbrot")
  buttonTest.onAction = (event: ActionEvent) => {
    println("début")
    mandelbrot.display("Mandelbrot",dimension,slider.getValue.toInt)
    //mandelbrot.display("Mandelbrot",new Dimensions((-0.562238-2.02837e-006 ,-0.562238+2.02837e-006),(-0.642828-2.02837e-006,-0.642828+0.272479)),800)
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
      mandelbrot.display("Mandelbrot", dimension, savedSlider.toInt)
    }
    mainPane.onScroll = (event: ScrollEvent) => {
      //si deltaY > 0 on zoom
      val zoomFactor:Int = 2
      val point:(Double,Double) = (event.x,event.y)
      val complexPoint: Complex = calculus.pixelToComplex(point._1,point._2,dim._1,dim._2,dimension.getX._1,dimension.getX._2,dimension.getY._1,dimension.getY._2)
      //println(point)
      //println(complexPoint.getReal,complexPoint.getImaginary)
      if (event.deltaY > 0 ) {
        print("X:")
        println(dimension.XX)
        print("Y:")
        println(dimension.YY)
        val currentScaleX:Double = dimension.XX/zoomFactor
        val currentScaleY:Double = dimension.YY/zoomFactor
        val newX:(Double,Double) = (complexPoint.getReal - currentScaleX/2,complexPoint.getReal + currentScaleX/2)
        val newY:(Double,Double) = (complexPoint.getImaginary - currentScaleY/2,complexPoint.getImaginary + currentScaleY/2)
        dimension = new Dimensions(newX,newY)
        println(newX,newY)
        mandelbrot.display("Mandelbrot",new Dimensions(newX,newY),slider.getValue().toInt)
      }
/*       //sinon on dezoom
      else if (event.deltaY < 0) {
        val currentScaleX = dimension.XX*zoomFactor
        val currentScaleY = dimension.YY*zoomFactor
        val newX = (complexPoint.getReal - currentScaleX/2,complexPoint.getReal + currentScaleX/2)
        val newY = (complexPoint.getImaginary - currentScaleY/2,complexPoint.getImaginary + currentScaleY/2)
        dimension = new Dimensions((dimension.getX._1*zoomFactor,dimension.getX._2*zoomFactor),(dimension.getY._1*zoomFactor,dimension.getY._2*zoomFactor))
        mandelbrot.display("Mandelbrot",dimension,slider.getValue().toInt)
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
  val fromComplex = new ToolBar()
  val inputReal = new TextField()
  val inputImaginary = new TextField()
  val drawnFrom = new Button("Drawn from complex")
  drawnFrom.onAction = (event:ActionEvent) => {
    mandelbrot.display("Fractal",dimension,slider.getValue().toInt,new Complex(inputReal.getText().toDouble, inputImaginary.getText().toDouble))
  }
  fromComplex.items.addAll(inputReal,inputImaginary,drawnFrom)
  val toolBar = new ToolBar();
  toolBar.items.addAll(reset,buttonTest,Repeat,Stop,slider)
  println("Vers la fin")
  mainPane.children.add(fromComplex)
  mandelbrotScene.root = mainPane
}