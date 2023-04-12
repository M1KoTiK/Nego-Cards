package m1k.kotik.negocards.fragments.pages

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.Companion.searchableListCanvasObjectTypes
import m1k.kotik.negocards.databinding.FragmentCreateCanvasQRCBinding
import m1k.kotik.negocards.fragments.utils_fragment.IOnBackPressedListener


class CreateCanvasQRCFragment : Fragment(), IOnBackPressedListener {
    private var selectedItemPosition : Int = -1
    private var binding: FragmentCreateCanvasQRCBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateCanvasQRCBinding.inflate(inflater,container,false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle = this.requireArguments()
        binding?.textView3?.text = ""
        binding?.view?.changeViewSize(bundle.getInt("width"),bundle.getInt("height"))
        binding?.view?.requestLayout()
        binding?.view?.onCurrentSelectedObjectChange = {
            if(binding?.view?.currentSelectedObject!=null){
                val selectedObject = binding?.view?.currentSelectedObject
                binding?.buttonminus?.setImageResource(R.drawable.minus)
                binding?.textView3?.text = selectedObject!!.encode()
            }
            else{
                binding?.buttonminus?.setImageResource(R.drawable.grayminus)
                binding?.textView3?.text = ""
            }
        }
        binding?.buttonminus?.setOnClickListener {
            if(binding?.view?.currentSelectedObject!=null) {
                binding?.view?.currentSelectedObject!!.isSelectMode = false
                binding?.view?.currentSelectedObject!!.isEditMode = false
                binding?.view?.deleteSelectedObject()
                binding?.view?.invalidate()
            }
        }
        binding?.buttonplus?.setOnClickListener {
            selectedItemPosition = -1
            val popWindow= m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.PopupWindow()
            popWindow.setup(
                requireActivity(),
                R.layout.add_canvas_object_popup,
            700, 700)
            popWindow.show(20,300,Gravity.TOP or Gravity.LEFT)
            val typesObject: MutableList<String> = mutableListOf()
            val addObject: Button = popWindow.popupView!!.findViewById(R.id.buttonAddObject)
            val autoCompleteObjectTypes: AutoCompleteTextView = popWindow.popupView!!.findViewById(R.id.ChoiceObjectType)
            addObject.setOnClickListener {
                if(selectedItemPosition >=0){
                    val selectedItem = typesObject[selectedItemPosition]
                    Toast.makeText(requireActivity(), "Selected: ${selectedItemPosition} Count= ${binding?.view?.objects?.size}", Toast.LENGTH_SHORT).show()
                    for (type in searchableListCanvasObjectTypes) {
                        if (selectedItem == type.visibleName){

                            binding?.view?.addCanvasObjects(type.classType)
                            binding?.view?.invalidate()
                            selectedItemPosition = -1
                        }
                    }
                }
                popWindow.close()
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
                            //Действие при выборе пункта выпадающего списка
                        }
                    }
                }
            }
        }


        binding?.textView3?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                //cObj.clear()
                try {
                   // testObj.applyCode(binding?.textView3?.text.toString())
                    //cObj.add(testObj)
                }
                catch(e:Exception){
                    Toast.makeText(requireActivity(),e.message,Toast.LENGTH_SHORT).show()
                }
               // binding?.view?.setCanvasObjects(cObj)
                binding?.view?.invalidate()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onBackPressed() {
        Toast.makeText(requireActivity(),"backpressed", Toast.LENGTH_SHORT).show()
    }
}