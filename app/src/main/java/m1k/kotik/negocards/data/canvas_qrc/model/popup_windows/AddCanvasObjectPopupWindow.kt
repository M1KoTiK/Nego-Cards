package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.*
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObjectType
import m1k.kotik.negocards.data.canvas_qrc.model.listCanvasObjectTypes

class AddCanvasObjectPopupWindow(var onAddCanvasObject: (CanvasObjectType)->Unit,
                                 var onChangeSelectedObject: ()->Unit): MovablePopupWindowDefault()  {
    var selectedItemPosition = -1
    fun setup(context: Context,
              height:Int,
              width:Int,
              isOutsideTouchable: Boolean = true,
              isFocusable: Boolean = true){
        super.setup(context,R.layout.add_canvas_object_popup,height,width,isOutsideTouchable,isFocusable)
    }

    override fun onPressUp(event: MotionEvent?) {
    }

    override fun onPressDown(event: MotionEvent?) {
        move(event!!.x.toInt(),event!!.y.toInt())
    }

    override fun onMove(event: MotionEvent?) {
        move(event!!.x.toInt(),event!!.y.toInt())
    }

    override fun onCreate() {
        val typesObject: MutableList<String> = mutableListOf()
        val addObject: Button = this.popupView!!.findViewById(R.id.buttonAddObject)
        val autoCompleteObjectTypes: AutoCompleteTextView = this.popupView!!.findViewById(R.id.ChoiceObjectType)
        addObject.setOnClickListener {
            if(selectedItemPosition >=0){
                val selectedItem = typesObject[selectedItemPosition]
                for (type in listCanvasObjectTypes) {
                    if (selectedItem == type.visibleName){
                        onAddCanvasObject.invoke(type)
                        selectedItemPosition = -1
                    }
                }
            }
            this.close()
        }
        for (type in listCanvasObjectTypes){
            if(!type.isSubsection) {
                typesObject.add(type.visibleName)
            }
        }
        val arrayAdapter = ArrayAdapter(this.context!!,R.layout.dropdown_item,typesObject)
        autoCompleteObjectTypes.setAdapter(arrayAdapter)
        autoCompleteObjectTypes.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
            if(selectedItemPosition != position){
                selectedItemPosition = position
                val selectedItem = parent.getItemAtPosition(position).toString()
                for (type in listCanvasObjectTypes){
                    if (type.visibleName == selectedItem){
                        onChangeSelectedObject.invoke()
                    }
                }
            }
        }
    }
}