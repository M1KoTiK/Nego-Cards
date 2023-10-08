package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import kotlin.reflect.KClass

class ComplementarySerializer(requiredObjectMap: Map<String, KClass<*>>)
    : DefaultSerializer(requiredObjectMap)
{
        fun multipleSerialize(list: MutableList<ISerializationObject>):String{
            var outputSting =  ""
            for (item in list){
                outputSting = super.serialize(item) + parser.converterSet.objectSeparator
            }
            return outputSting
        }

        fun <T> multipleDeserialize(serializedString: String): List<T>{
            var index = 0
        val outputList = mutableListOf<T>()
            while ( index < serializedString.length){
                val deserializeObject = super.deserialize<T>(serializedString.drop(index))
                index = parser.serializationStringSplitObjectValueIndex
                if(deserializeObject == null ){
                    continue
                }
                outputList.add(deserializeObject)
            }
            return outputList
        }
}