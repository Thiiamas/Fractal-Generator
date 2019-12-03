import API.Dimensions
import API.Complex
import API.Calculus
import scalafx.application.JFXApp
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, Pane}
import scalafx.scene.text.Text
import scalafx.animation.AnimationTimer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Slider, TextField, ToolBar,ToggleButton}
import scalafx.scene.input.{KeyEvent, MouseEvent}
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color._
import scalafx.scene.input.ScrollEvent
import javafx.scene.layout.TilePane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox

object Main extends JFXApp {
  //Variables
  val calculus = new Calculus()
  val dim = (1000,1000)
  var dimension = new Dimensions((-2,1),(-1.5,1.5))

  //Initialisation with the first scene
  val startScene = new Scene {
    fill = LightBlue
    val mandelbrot = new Button("Mandelbrot")
    mandelbrot.onAction = (event:ActionEvent) => {
      MainStage.scene = mandelbrotScene
    }
    val triangle = new Button("Triangle")
    triangle.onAction = (event:ActionEvent) => {
      MainStage.scene = polygonsScene
    }
    content = new TilePane (mandelbrot,triangle)
  }
  //Only stage of the application
  var MainStage:JFXApp.PrimaryStage = new JFXApp.PrimaryStage{
    title = "Fractal-Generator"
    scene = startScene
    height = dim._1
    width = dim._2
  }

  //Scene used for polygons
  val polygonsScene:Scene = new Scene
  val poly = new Polygons(dim)
  val trianglePane = new Pane {
    children = poly.getCanvas
  }
  val draw = new Button("Draw")
  draw.onAction = (event:ActionEvent) => {
      poly.display("Triangles")
    }
  trianglePane.children.add(draw)
  polygonsScene.root = trianglePane

  //Scene used for mandelbrot and other complex fractals
  val mandelbrotScene: Scene = new Scene
  var iteration: Int = 25
  val mandelbrot = new Mandelbrot((dim._1,dim._2))  //initialiser directement
  val dimTextX= new Text("X : ("+dimension.getX._1 + ","+dimension.getX._2 +")")
  val dimTextY = new Text("Y : ("+dimension.getY._1 + ","+dimension.getY._2 +")")
  val displayDim = new scalafx.scene.layout.VBox()
  displayDim.children.addAll(dimTextX,dimTextY)
  val manLayout = new BorderPane(mandelbrot.getCanvas)
  manLayout.setBottom(displayDim)
  val mainPane = new Pane {
    //mandelbrot.display("Mandelbrot",dimension,slider.getValue.toInt)
    children = manLayout
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
    mandelbrot.display("Mandelbrot",dimension,slider.getValue.toInt)
    //mandelbrot.display("Mandelbrot",new Dimensions((-0.562238-2.02837e-006 ,-0.562238+2.02837e-006),(-0.642828-2.02837e-006,-0.642828+0.272479)),800)
  }
  //Slider
  val slider = new Slider(10,1000,iteration)
  slider.showTickMarks = true
  slider.showTickLabels = true
  mandelbrot.display("Mandelbrot",dimension,slider.getValue.toInt)
  var lastTime = 0L
  val timer = AnimationTimer(t => {
    if (lastTime>0){
      val delta = (t-lastTime)/1e9
    }
/*     if (savedSlider != slider.getValue) {
      savedSlider = slider.getValue()
      mandelbrot.display("Mandelbrot", dimension, savedSlider.toInt)
    } */
    mainPane.onScroll = (event: ScrollEvent) => {
      //si deltaY > 0 on zoom
      val zoomFactor:Int = 2
      val point:(Double,Double) = (event.x,event.y)
      val complexPoint: Complex = calculus.pixelToComplex(point._1,point._2,dim._1,dim._2,dimension.getX._1,dimension.getX._2,dimension.getY._1,dimension.getY._2)
      //println(point)
      //println(complexPoint.getReal,complexPoint.getImaginary)
      if (event.deltaY > 0 ) {
        val currentScaleX:Double = dimension.XX/zoomFactor
        val currentScaleY:Double = dimension.YY/zoomFactor
        val newX:(Double,Double) = (complexPoint.getReal - currentScaleX/2,complexPoint.getReal + currentScaleX/2)
        val newY:(Double,Double) = (complexPoint.getImaginary - currentScaleY/2,complexPoint.getImaginary + currentScaleY/2)
        dimension = new Dimensions(newX,newY)
        mandelbrot.display("Mandelbrot",new Dimensions(newX,newY),slider.getValue().toInt)
      }
      //sinon on dezoom
      else if (event.deltaY < 0) {
        val currentScaleX = dimension.XX*zoomFactor
        val currentScaleY = dimension.YY*zoomFactor
        val newX = (complexPoint.getReal - currentScaleX/2,complexPoint.getReal + currentScaleX/2)
        val newY = (complexPoint.getImaginary - currentScaleY/2,complexPoint.getImaginary + currentScaleY/2)
        dimension = new Dimensions((dimension.getX._1*zoomFactor,dimension.getX._2*zoomFactor),(dimension.getY._1*zoomFactor,dimension.getY._2*zoomFactor))
        mandelbrot.display("Mandelbrot",dimension,slider.getValue().toInt)
      }
    }
    lastTime = t
  })
  val zoom = new ToggleButton("Zoom")
  var zX = 0.0
  var zY = 0.0
  val selection = new javafx.scene.shape.Rectangle()
  mainPane.onMousePressed = (event:MouseEvent) =>{
    if (zoom.isSelected()){
      zX = event.x
      zY = event.y
      selection.setX(event.getX());
      selection.setY(event.getY());
      selection.setFill(null); // transparent 
      selection.setStroke(BLACK); // border
      selection.getStrokeDashArray().add(10.0);
      mainPane.getChildren().add(selection);
    }
  }
  mainPane.onMouseDragged = (event:MouseEvent) => {
    if (zoom.isSelected()){
      selection.setWidth(Math.abs(event.getX() - zX));
      selection.setHeight(Math.abs(event.getY() - zY));
      selection.setX(Math.min(zX, event.getX()));
      selection.setY(Math.min(zY, event.getY()));
    }
  }
  mainPane.onMouseReleased = (event:MouseEvent) => {
    if (zoom.isSelected()){
      val startPoint: Complex = calculus.pixelToComplex(zX,zY,dim._1,dim._2,dimension.getX._1,dimension.getX._2,dimension.getY._1,dimension.getY._2)
      val endPoint: Complex = calculus.pixelToComplex(event.x,event.y,dim._1,dim._2,dimension.getX._1,dimension.getX._2,dimension.getY._1,dimension.getY._2)
      dimension = new Dimensions((startPoint.getReal,endPoint.getReal),(startPoint.getImaginary,endPoint.getImaginary))
      if (mandelbrot.vName == "Man") mandelbrot.display("Mandelbrot",dimension,slider.getValue().toInt)
      else if (mandelbrot.vName == "Other") mandelbrot.display("Fractal",dimension,slider.getValue().toInt,new Complex(inputReal.getText().toDouble, inputImaginary.getText().toDouble))
      mainPane.children.remove(selection)
      dimTextX.setText("X : ("+dimension.getX._1 + ","+dimension.getX._2 +")")
      dimTextY.setText("Y : ("+dimension.getY._1 + ","+dimension.getY._2 +")")
    }
  }
  val Repeat = new Button("Repeat")
  Repeat.onAction = (event: ActionEvent) => {
    timer.start()
  }
  val Stop = new Button("Stop")
  Stop.onAction = (event: ActionEvent) => {
    timer.stop()
  }
  val DrawFromPoint = new Button("FromPoint")
  val toolBar = new ToolBar();
  toolBar.items.addAll(reset,buttonTest,zoom,slider,DrawFromPoint)
  manLayout.setTop(toolBar)
  //mainPane.children.add(toolBar)

  // Toolbar to draw any fractal from a point
  val fromComplex = new ToolBar()
  val inputReal = new TextField()
  val inputImaginary = new TextField()
  val drawnFrom = new Button("Drawn from complex")
  val selectPoint = new ToggleButton("SelectPoint")
  mainPane.onMouseClicked = (event:MouseEvent) => {
    if (selectPoint.isSelected()) {
      val point = (event.x, event.y)
      val complexPoint: Complex = calculus.pixelToComplex(point._1,point._2,dim._1,dim._2,dimension.getX._1,dimension.getX._2,dimension.getY._1,dimension.getY._2)
      inputReal.setText(complexPoint.getReal.toString())
      inputImaginary.setText(complexPoint.getImaginary.toString())
      mandelbrot.display("Fractal",dimension,slider.getValue().toInt,new Complex(inputReal.getText().toDouble, inputImaginary.getText().toDouble))
    }
  }
  drawnFrom.onAction = (event:ActionEvent) => {
    mandelbrot.display("Fractal",dimension,slider.getValue().toInt,new Complex(inputReal.getText().toDouble, inputImaginary.getText().toDouble))
  }
  val returnMan = new Button("Return to Man")
  returnMan.onAction = (event:ActionEvent) => {
    manLayout.setTop(toolBar)
    selectPoint.setSelected(false)
/*     mainPane.children.remove(fromComplex)
    mainPane.children.add(toolBar) */
  }
  fromComplex.items.addAll(inputReal,inputImaginary,slider,drawnFrom,selectPoint,returnMan)
  DrawFromPoint.onAction = (event:ActionEvent) => {
    manLayout.setTop(fromComplex)
    zoom.setSelected(false)
/*     mainPane.children.remove(toolBar)
    mainPane.children.add(fromComplex) */
  }
  mandelbrotScene.root = mainPane
}