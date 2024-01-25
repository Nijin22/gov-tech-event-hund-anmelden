import commons.serviceportal.forms.FormValidator
import spock.lang.Specification

class ValidFormSpecification extends Specification {
  def "Verify form is valid"() {
    when:
    List<String> forms = [
            "govTecEvent_HundAnmelden_ApplicantForm-v1.0-de.json", // TODO: Update the file names
            "govTecEvent_HundAnmelden_PreliminaryForm-v1.0-de.json",
            "govTecEvent_HundAnmelden_SummaryForm-v1.0-de.json",
    ]
    forms.each { form ->
      new FormValidator(getClass().getResourceAsStream(form).text).validate()
    }

    then:
    noExceptionThrown()
  }
}
