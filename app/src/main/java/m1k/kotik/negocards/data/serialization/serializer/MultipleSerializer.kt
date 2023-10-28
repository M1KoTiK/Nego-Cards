package m1k.kotik.negocards.data.serialization.serializer

import com.bumptech.glide.load.model.ByteArrayLoader.Converter
import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import kotlin.reflect.KClass

class MultipleSerializer(parser:ISerializationParser, requiredObjectMap: Map<String, KClass<*>>)
    : DefaultSerializer(parser, requiredObjectMap)
{
        fun multipleSerialize(list: MutableList<ISerializationObject>):String{
            var outputSting =  ""
            for (item in list){
                outputSting += super.serialize(item) + parser.converterSet.objectSeparator
            }
            return outputSting
        }

        fun <T> multipleDeserialize(serializedString: String): List<T>{
            var index = 0
        val outputList = mutableListOf<T>()
            while ( index < serializedString.length){
                val key = super.getSerializeObjectKey(serializedString)?: break
                index = parser.serializationStringSplitObjectValueIndex
                if(index!= 0) {
                    val deserializeObject = super.deserialize<T>(
                        serializedString.drop(
                            index + parser.converterSet.splitSign.count() + key.count()
                        )
                    )?: break
                    outputList.add(deserializeObject)
                }
                else{
                   val deserializeObject = super.deserialize<T>(serializedString)?: break
                    outputList.add(deserializeObject)
                }

            }
            return outputList
        }
}