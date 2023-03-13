package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.ArcShape
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.QadrilShape
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape

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
            isAntiAlias = true
            isDither = true
        }
        val rect = RectF(0f,0f,canvas?.width?.toFloat()!!,canvas?.height?.toFloat()!!)
        canvas?.drawRoundRect(rect,25f,25f,paint)
        for(obj in objects_){
            when(obj.typeObj){
                ObjectType.Text ->{
                   obj as TextObject
                    paint.apply {
                        color = Color.parseColor("#${obj.color}")
                        style = Paint.Style.FILL
                        isAntiAlias = true
                        isDither = true
                        textSize = obj.fontSize.toFloat()
                    }
                    canvas?.drawText(obj.text,obj.posX.toFloat(),obj.posY.toFloat(),paint)
                }
                ObjectType.Image->{

                }
                ObjectType.Shape->{
                    obj as ShapeObject
                    paint.apply {
                        color = Color.parseColor("#${obj.color}")
                        style = Paint.Style.FILL
                        strokeWidth= 5F
                        isAntiAlias = true
                        isDither = true
                    }
                    when(obj.shapeType){
                        ShapeType.Rect->{
                            canvas?.drawRect(obj.posX.toFloat(),
                                obj.posY + obj.height.toFloat(),
                                obj.posX + obj.width.toFloat(),
                                obj.posY.toFloat(),
                                paint
                            )
                        }
                        ShapeType.Oval->{

                            canvas?.drawOval(obj.posX.toFloat(),
                                obj.posY + obj.height.toFloat(),
                                obj.posX + obj.width.toFloat(),
                                obj.posY.toFloat(),
                                paint)
                        }
                        ShapeType.Arc->{
                            obj as ArcShape
                            canvas?.drawArc(obj.posX.toFloat(),
                                obj.posY.toFloat(),
                                obj.posX + obj.width.toFloat(),
                                obj.posY+ obj.height.toFloat(),
                                obj.startAngle.toFloat(),
                                obj.sweepAngle.toFloat(),
                                obj.useCenter,
                                paint
                                )
                        }
                        ShapeType.RectRound->{
                            obj as RectRShape
                            canvas?.drawRoundRect(obj.posX.toFloat(),
                                obj.posY.toFloat(),
                                obj.posX + obj.width.toFloat(),
                                obj.posY+ obj.height.toFloat(),
                                obj.leftCorner.toFloat(),
                                obj.rightCorner.toFloat(),
                                paint
                            )
                        }
                        ShapeType.Quadril->{
                            obj as QadrilShape
                            drawQuadrilateral(canvas,paint,
                                obj.posX,obj.posY,
                                obj.bottomLeftX, obj.bottomLeftY,
                                obj.topLeftX, obj.topLeftY,
                                obj.bottomRightX, obj.bottomRightY,
                                obj.topRightX, obj.topRightY
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