package springfox.test.contract.swagger

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module
import com.wordnik.swagger.models.Operation
import com.wordnik.swagger.models.Scheme
import spock.lang.Specification
import springfox.documentation.swagger2.configuration.Swagger2JacksonModule

import static com.google.common.collect.Lists.*

class SerializationSpec extends Specification {
  def "serialization rules are preserved  when module registration order is switched" () {
    given:
      ObjectMapper mapper = new ObjectMapper()
    and:
      Swagger2JacksonModule.maybeRegisterModule(mapper)
      Hibernate4Module hibernate4Module = new Hibernate4Module()
      hibernate4Module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
      mapper.registerModule(hibernate4Module)
    when:
      Operation operation = new Operation().operationId("test").schemes(newArrayList())
    then:
      "{\"operationId\":\"test\"}".equals(mapper.writer().writeValueAsString(operation))
  }

  def "serialization rules are preserved for non-empty lists when module registration order is switched" () {
    given:
      ObjectMapper mapper = new ObjectMapper()
    and:
      Swagger2JacksonModule.maybeRegisterModule(mapper)
      Hibernate4Module hibernate4Module = new Hibernate4Module()
      hibernate4Module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
      mapper.registerModule(hibernate4Module)
    when:
      Operation operation = new Operation().operationId("test").schemes(newArrayList(Scheme.HTTP))
    then:
      "{\"operationId\":\"test\",\"schemes\":[\"http\"]}".equals(mapper.writer().writeValueAsString(operation))
  }

  def "serialization rules are preserved" () {
    given:
      ObjectMapper mapper = new ObjectMapper()
    and:
      Hibernate4Module hibernate4Module = new Hibernate4Module()
      hibernate4Module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
      mapper.registerModule(hibernate4Module)
      Swagger2JacksonModule.maybeRegisterModule(mapper)
    when:
      Operation operation = new Operation().operationId("test").schemes(newArrayList())
    then:
      "{\"operationId\":\"test\"}".equals(mapper.writer().writeValueAsString(operation))
  }

  def "serialization rules are preserved for non-empty lists" () {
    given:
      ObjectMapper mapper = new ObjectMapper()
    and:
      Hibernate4Module hibernate4Module = new Hibernate4Module()
      hibernate4Module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
      mapper.registerModule(hibernate4Module)
      Swagger2JacksonModule.maybeRegisterModule(mapper)
    when:
      Operation operation = new Operation().operationId("test").schemes(newArrayList(Scheme.HTTP))
    then:
      "{\"operationId\":\"test\",\"schemes\":[\"http\"]}".equals(mapper.writer().writeValueAsString(operation))
  }
}
