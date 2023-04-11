package m1k.kotik.negocards.data.canvas_qrc.model

class DoublecClickChecker(private val delay: Long , val OnDoubleClickAction: ()->Unit) {
    private val listClickTime = mutableListOf<Long>()
    private fun check(){
        if(listClickTime[1]-listClickTime[0] < delay )
        {
            OnDoubleClickAction.invoke()
            listClickTime.clear()
        }
        else
        {
            listClickTime[0] = listClickTime[1]
        }
    }
    public fun click(){
        if(listClickTime.count() < 2){
            listClickTime.add(System.currentTimeMillis())
            if(listClickTime.count()==2){
                check()
            }
        }
        else
        {
            listClickTime[1] = System.currentTimeMillis()
            check()
        }
    }
}