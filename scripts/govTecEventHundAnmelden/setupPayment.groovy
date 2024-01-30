package govTecEventHundAnmelden

import de.seitenbau.serviceportal.scripting.api.v1.ScriptingApiV1
import de.seitenbau.serviceportal.scripting.api.v1.payment.BerlinPaymentConfigV1
import de.seitenbau.serviceportal.scripting.api.v1.payment.DisplayConfigV1
import de.seitenbau.serviceportal.scripting.api.v1.payment.EpayBlPaymentConfigV1
import de.seitenbau.serviceportal.scripting.api.v1.payment.PaymentConfigV1
import de.seitenbau.serviceportal.scripting.api.v1.payment.TransactionConfigV1
import groovy.json.JsonOutput

ScriptingApiV1 api = apiV1 // Variable is automatically set by Serviceportal process engine

// Note: You probably want to read most of these values from process parameters.
// Use `api.getStartParameter().parameters.get(parameterName)` to do so.

// Setup paymentConfig
PaymentConfigV1 paymentConfig = new BerlinPaymentConfigV1()
paymentConfig.username = api.getStartParameter().parameters.get("paymentUsername")
paymentConfig.password = api.getStartParameter().parameters.get("paymentPassword")
paymentConfig.authId = api.getStartParameter().parameters.get("paymentAuthId")
paymentConfig.authPassword = api.getStartParameter().parameters.get("paymentAuthPassword\n")
api.setVariable("paymentConfig", JsonOutput.prettyPrint(JsonOutput.toJson(paymentConfig)))

// Setup transactionConfig
// See https://doku.pmp.seitenbau.com/display/DFO/transactionConfig for expected values and other optional parameters
TransactionConfigV1 transactionConfig = new TransactionConfigV1()
String costInCent = api.getStartParameter().parameters.get("costInCent")
assert costInCent.isLong(): "Failed to configure payment. Process parameter 'costInCent' (value '$costInCent') " +
        "cannot be cast to data type 'long'."
transactionConfig.betrag = costInCent.toLong()
transactionConfig.verwendungszweck = "GovTechEvent Demozahlung Hund"
api.setVariable("transactionConfig", JsonOutput.prettyPrint(JsonOutput.toJson(transactionConfig)))

// Setup displayConfig
DisplayConfigV1 displayConfig = new DisplayConfigV1()
displayConfig.skipSuccessPage = true // Success page is not necessary, as the process provides it's own "last page"
api.setVariable("displayConfig", JsonOutput.prettyPrint(JsonOutput.toJson(displayConfig)))
