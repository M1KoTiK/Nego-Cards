package m1k.kotik.negocards.data.serialization.parser

/**
 * Представляет разделенный на части ISerializationObject,
 * где objKey - это ключ сериализуемого объекта, а object values - словарь
 * в котором ключ - это значение key которое предписывается атрибутом SerializeMember("key")
 * а TypedValue представляет собой тип объекта и его значение
 */
class SeparatedObject(val objKey:String, val objectValues: Map<String,TypedValue>)