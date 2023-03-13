package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.model.*

open class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint: Paint = Paint()
    private lateinit var mainCanvas : Canvas
    val objects: List<CanvasObject>
        get() = objects_

    protected var objects_: MutableList<CanvasObject> = mutableListOf()

    fun setCanvasObjects(objects: List<CanvasObject>) {
        objects_ = objects.toMutableList()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }
        val rect = RectF(0f,0f,canvas?.width?.toFloat()!!,canvas?.height?.toFloat()!!)
        canvas?.drawRoundRect(rect,25f,25f,paint)
        for(obj in objects_){
            when(obj.typeObj){
                ObjectType.Text ->{
                   val txObj =  obj as TextObject
                    paint.apply {
                        color = txObj.color
                        style = Paint.Style.FILL
                        isAntiAlias = true
                        textSize = txObj.fontSize.toFloat()
                    }
                    canvas?.drawText(txObj.text,txObj.posX.toFloat(),txObj.posY.toFloat(),paint)
                }
                ObjectType.Image->{

                }
                ObjectType.Shape->{
                    val shObj =  obj as ShapeObject
                    paint.apply {
                        color = shObj.color
                        style = Paint.Style.FILL
                        strokeWidth= 5F
                        isAntiAlias = true
                        isDither = true
                    }
                    when(shObj.shapeType){
                        ShapeType.Rect->{
                            canvas?.drawRect(shObj.posX.toFloat(),
                                shObj.posY + shObj.height.toFloat(),
                                shObj.posX + shObj.width.toFloat(),
                                shObj.posY.toFloat(),
                                paint
                            )
                        }
                        ShapeType.Oval->{
                            canvas?.drawOval(shObj.posX.toFloat(),
                                shObj.posY + shObj.height.toFloat(),
                                shObj.posX + shObj.width.toFloat(),
                                shObj.posY.toFloat(),
                                paint)
                        }
                        ShapeType.Arc->{
                            canvas?.drawArc(shObj.posX.toFloat(),
                                shObj.posY.toFloat(),
                                shObj.posX + shObj.width.toFloat(),
                                shObj.posY+ shObj.height.toFloat(),
                                shObj.leftCorner.toFloat(),
                                shObj.rightCorner.toFloat(),
                                true,
                                paint
                                )
                        }
                        ShapeType.RectRound->{
                            canvas?.drawRoundRect(shObj.posX.toFloat(),
                                shObj.posY.toFloat(),
                                shObj.posX + shObj.width.toFloat(),
                                shObj.posY+ shObj.height.toFloat(),
                                shObj.leftCorner.toFloat(),
                                shObj.rightCorner.toFloat(),
                                paint
                            )
                        }
                        ShapeType.RectF->{
                            canvas?.drawRoundRect(shObj.posX.toFloat(),
                                shObj.posY.toFloat(),
                                200F,
                                shObj.posY+ shObj.height.toFloat(),
                                shObj.leftCorner.toFloat(),
                                shObj.rightCorner.toFloat(),
                                paint
                            )
                        }
                        ShapeType.Quadril->{
                            drawQuadrilateral(canvas,paint,
                                shObj.posX,shObj.posY,
                                shObj.bottomLeftX, shObj.bottomLeftY,
                                shObj.topLeftX, shObj.topLeftY,
                                shObj.bottomRightX, shObj.bottomRightY,
                                shObj.topRightX, shObj.topRightY
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(900,600)
    }
    fun drawTriangle(canvas: Canvas, paint: Paint, x:Int, y:Int, width:Int) {
        val halfWidth : Int = width / 2
        val path: Path = Path()
        path.moveTo(x.toFloat(), (y - halfWidth).toFloat()) // Top
        path.lineTo((x - halfWidth).toFloat(), (y + halfWidth).toFloat()); // Bottom left
        path.lineTo((x + halfWidth).toFloat(), (y + halfWidth).toFloat()); // Bottom right
        path.lineTo(x.toFloat(), (y - halfWidth).toFloat()); // Back to Top
        path.close();
        canvas.drawPath(path, paint);
    }
    fun drawQuadrilateral(canvas: Canvas, paint: Paint, x:Int, y:Int,
                          bottomLeftX: Int, bottomLeftY: Int,
                          topLeftX: Int, topLeftY: Int,
                          bottomRightX: Int, bottomRightY: Int,
                          topRightX: Int, topRightY: Int) {

        val path: Path = Path()
        path.moveTo(x.toFloat(), y.toFloat()) // Top
        path.lineTo(bottomLeftX.toFloat(), bottomLeftY.toFloat())
        path.lineTo(topLeftX.toFloat(), topLeftY.toFloat())
        path.lineTo(topRightX.toFloat(), topRightY.toFloat())
        path.lineTo(bottomRightX.toFloat(), bottomRightY.toFloat())
        path.close();
        canvas.drawPath(path, paint);
    }
}