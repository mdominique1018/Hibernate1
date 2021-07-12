const MailDev = require('maildev')
const nodemailer = require("nodemailer");

const maildev = new MailDev()

maildev.listen()

// Handle new emails as they come in
maildev.on('new', (email) => {
    console.log(`Received new email with subject: ${email.subject}`)
})

maildev.getAllEmail = function (param) {

}
// Get all emails
maildev.getAllEmail((err, emails) => {
    if (err) return console.log(err)
    console.log(`There are ${emails.length} emails`)
})
nodemailer.createTransport = function (smtp, param2) {


}
const transport = nodemailer.createTransport("SMTP", {
    port: 1025,
    // other settings...
});