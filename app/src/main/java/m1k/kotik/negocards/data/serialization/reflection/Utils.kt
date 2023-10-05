package m1k.kotik.negocards.data.serialization.reflection

import m1k.kotik.negocards.data.serialization.parser.TypedValue
import m1k.kotik.negocards.data.serialization.serializationObject.SeriаlizationMember
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import org.reflections.Reflections
import kotlin.reflect.*
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

fun findImplementationsOfInterface(interfaceType: KClass<*>, packageName: String): Set<KClass<*>> {
    val reflections = Reflections(packageName)
    val subTypes = reflections.getSubTypesOf(interfaceType.java)
    val implementingClasses = mutableSetOf<KClass<*>>()
    for (subType in subTypes) {
        val implementedInterfaces = subType.kotlin.allSuperclasses
        if (implementedInterfaces.contains(interfaceType)) {
            implementingClasses.add(subType.kotlin)
        }
    }
    return implementingClasses
}


inline fun <reified T> writeOnKey(key:String, value: Any, serializationObject: T){
        println("writeOnKeyValue - ${value}")
        println("writeOnKeyKey - ${key}")
        val properties = serializationObject!!::class.memberProperties
        for(prop in properties){
            var annotation = prop.findAnnotation<SeriаlizationMember>()
            if(annotation != null && annotation.Key == key){
                if (prop is KMutableProperty1<*, *>) {
                    val propType = prop.returnType.jvmErasure // Тип свойства prop
                    val valueType = value::class // Тип value
                    if (propType.isInstance(value)) {
                        // Типы совпадают или value может быть приведен к типу propType
                        prop.setter.call(serializationObject, value)
                    } else {
                        // Типы не совпадают
                        println("Тип value ($valueType) не совпадает с типом свойства ($propType).")
                    }
                } else {
                    // prop не является изменяемым свойством
                    println("prop не является изменяемым свойством.")
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
fun getMemberKeysAndTypes(sObj: ISerializationObject): MutableMap<String, KType>{
    val memberAnnotationList = getMemberAnnotation(sObj)
    val outputMap = mutableMapOf<String,KType>()
    val properties = sObj::class.memberProperties
    for (prop in properties){
        val annotation = prop.findAnnotation<SeriаlizationMember>()
        if(annotation!= null) {
            outputMap[annotation.Key] = prop.returnType
        }
    }
    return outputMap
}

fun getMemberKeysAndTypedValue(sObj: ISerializationObject): MutableMap<String, TypedValue>{
    val memberAnnotationList = getMemberAnnotation(sObj)
    val outputMap = mutableMapOf<String,TypedValue>()
    val properties = sObj::class.memberProperties
    for (prop in properties){
        val annotation = prop.findAnnotation<SeriаlizationMember>()
        if(annotation!= null) {
            val type = prop.returnType
            val value = prop.getter.call(sObj) ?: continue
            outputMap[annotation.Key] = TypedValue(type, value)
        }
    }
    return outputMap
}



inline fun <reified T> getType() = T::class.java