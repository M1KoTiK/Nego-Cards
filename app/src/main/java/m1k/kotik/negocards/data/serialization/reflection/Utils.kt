package m1k.kotik.negocards.data.serialization.reflection

import m1k.kotik.negocards.data.serialization.serializationObject.SeriаlizationMember
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import kotlin.reflect.*
import kotlin.reflect.full.*

inline fun <reified T: ISerializationObject, V> writeOnKey(key:String, value: V, serializationObject: T){
        val properties = serializationObject::class.memberProperties
        for(prop in properties){
            var annotation = prop.findAnnotation<SeriаlizationMember>()
            if(annotation != null && annotation.Key == key){
                if(prop is KMutableProperty1){
                    prop.setter.call(serializationObject, value)
                }
            }
        }
}
fun getMemberAnnotation(sObj: ISerializationObject): MutableList<SeriаlizationMember>{
    val outputList: MutableList<SeriаlizationMember> = mutableListOf()
    val properties = sObj::class.memberProperties
    for (prop in properties){
        val annotation = prop.findAnnotation<SeriаlizationMember>()
        if(annotation!= null) {
            outputList.add(annotation)
        }
    }
    return outputList
}
fun getMemberKeys(sObj: ISerializationObject): MutableList<String>{
    val memberAnnotationList = getMemberAnnotation(sObj)
    val keyList = mutableListOf<String>()
    for(annotation in memberAnnotationList){
        keyList.add(annotation.Key)
    }
    return keyList
}