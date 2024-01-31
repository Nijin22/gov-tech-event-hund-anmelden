package govTecEventSondernutzungStrasse

import commons.serviceportal.helpers.ServiceportalLogger
import commons.serviceportal.validators.MinimumAgeValidator
import de.seitenbau.serviceportal.scripting.api.v1.ScriptingApiV1
import de.seitenbau.serviceportal.scripting.api.v1.form.content.FormContentV1

ScriptingApiV1 api = apiV1 // Variable is automatically set by Serviceportal process engine

api.setVariable("isFormValid", true) // Initially, assume all input is correct
FormContentV1 preliminaryForm = api.getVariable("preliminaryForm") as FormContentV1

// Validate age
def birthdateField = preliminaryForm.fields."preliminaryFormGroup:0:birthdate"
Date birthdate = birthdateField.value as Date
assert birthdate != null
if (new MinimumAgeValidator(18).isAtLeastThatOld(birthdate)) {
  // Age OK!
} else {
  birthdateField.addValidationMessage("Bitte prüfen Sie das eingegebene Geburtsdatum. Sie müssen mindestens 18 Jahre " +
          "als sein, um diesen Antrag stellen zu können.")
  api.setVariable("isFormValid", false) // causes process to go back to previous form
}

api.setVariable("preliminaryForm", preliminaryForm)
