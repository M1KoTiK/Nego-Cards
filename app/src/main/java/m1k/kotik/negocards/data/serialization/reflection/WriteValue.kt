package m1k.kotik.negocards.data.serialization.reflection

import m1k.kotik.negocards.data.serialization.SeriаlizeMember
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

inline fun <reified T: ISerializationObject, V> writeOnKey(key:String, value: V, serializationObject: T){
    val properties = T::class.declaredMemberProperties

    for (property in properties) {
        val properties = T::class.declaredMemberProperties
        for (property in properties) {
            val annotation = property.findAnnotation<SeriаlizeMember>()
            if (annotation != null) {
                if (property is KProperty1<T, *>) {
                    val getter = property.getter
                    val setter = property.setter
                    setter.call(serializationObject, value)
}