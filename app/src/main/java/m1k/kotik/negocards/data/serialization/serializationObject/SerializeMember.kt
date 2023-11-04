package m1k.kotik.negocards.data.serialization.serializationObject

/**
 * Этим атрибутом помечаются свойства в классе реализующем интерфейс ISerializationObject для
 * того чтобы указать какие свойства должны быть сериализованы
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
annotation class SerializeMember(val Key: String)
