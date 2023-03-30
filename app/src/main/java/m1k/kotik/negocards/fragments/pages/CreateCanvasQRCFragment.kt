package m1k.kotik.negocards.fragments.pages

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.CanvasObjectSerializationTag
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.Companion.searchableListCanvasObjectTypes
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectShape
import m1k.kotik.negocards.databinding.FragmentCreateCanvasQRCBinding
import m1k.kotik.negocards.fragments.choiceParametersForQR.ChoiceParametersForCardFragment
import m1k.kotik.negocards.fragments.choiceParametersForQR.ChoiceParametersForTextFragment


class CreateCanvasQRCFragment : Fragment() {
    private var selectedItemPosition : Int = -1
    private var binding: FragmentCreateCanvasQRCBinding? = null
    val cObj = mutableListOf<CanvasObject>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateCanvasQRCBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testObj = RectShape(200,200,200,200,"FF181818",CanvasObjectSerializationTag.Style.Fill())
        cObj.add(testObj)

        binding?.button5?.setOnClickListener {
            binding?.view?.setCanvasObjects(cObj)
            binding?.view?.invalidate()
        }

        binding?.button6?.setOnClickListener {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView: View = inflater.inflate(R.layout.add_canvas_object_popup, null)
            val popupWindow = PopupWindow()
            popupWindow.isOutsideTouchable = true;
            popupWindow.isFocusable = true;
            popupWindow.contentView = popupView
            popupWindow.height = 800
            popupWindow.width = 800
            popupWindow.showAtLocation(popupView, Gravity.TOP or Gravity.LEFT,20,300)
            val typesObject: MutableList<String> = mutableListOf()
            val addObject: Button = popupView.findViewById(R.id.buttonAddObject)
            val autoCompleteObjectTypes: AutoCompleteTextView = popupView.findViewById(R.id.ChoiceObjectType)
            addObject.setOnClickListener {
                Toast.makeText(requireActivity(), "mmmm",Toast.LENGTH_SHORT).show()
                if(selectedItemPosition >0){
                    val selectedItem = typesObject[selectedItemPosition]
                    for (type in searchableListCanvasObjectTypes) {
                        if (selectedItem == type.visibleName)
                            type.classType
                    }
                }
            }
            for (type in searchableListCanvasObjectTypes){
                if(!type.isSubsection) {
                    typesObject.add(type.visibleName)
                }
            }
            val arrayAdapter = ArrayAdapter(requireActivity(),R.layout.dropdown_item,typesObject)
            autoCompleteObjectTypes.setAdapter(arrayAdapter)
            autoCompleteObjectTypes.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
                if(selectedItemPosition != position){
                    selectedItemPosition = position
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    //Toast.makeText(requireActivity(), "Selected: $position", Toast.LENGTH_SHORT).show()
                    for (type in searchableListCanvasObjectTypes){
                        if (type.visibleName == selectedItem){
                            cObj.add(type.classType)
                            binding?.view?.setCanvasObjects(cObj)
                            binding?.view?.invalidate()

                        }
                    }
                }
            }
        }


        binding?.textView3?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                cObj.clear()
                try {
                    testObj.applyCode(binding?.textView3?.text.toString())
                    cObj.add(testObj)
                }
                catch(e:Exception){
                    Toast.makeText(requireActivity(),e.message,Toast.LENGTH_SHORT).show()
                }
                binding?.view?.setCanvasObjects(cObj)
                binding?.view?.invalidate()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}