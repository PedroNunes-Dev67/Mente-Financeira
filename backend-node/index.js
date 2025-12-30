require('dotenv').config();
const express = require('express');
const app = express();
const nodemailer = require('nodemailer');

const transport = nodemailer.createTransport({
    host: 'smtp.gmail.com',
    port:465,
    secure: true,
    auth: {
        user: process.env.USER,
        pass:process.env.PASS
    }
})

app.use(express.json());

app.listen(3000, () => {
    console.log("Servidor rodando na porta 3000.")
})


app.post("/email-verificacao", (req,res) => {

    const {email, token} = req.body;

    transport.sendMail({
        from:`Mente Financeira <${process.env.USER}>`,
        to:`${email}`,
        subject: 'Equipe Mente Financeira💵',
        html:`<h2>Olá, aqui é a equipe do <strong style="color:green;">Mente Financeira</strong>💰💵</h2> <h4>Para melhorar a segurança e sua experiência, segue o link para validar seu email:</h4>`+
                `<a href="http://localhost:5500/verificar-email?token=${token}">http://localhost:5500/verificar-email?token=${token}</a>`+
                `<p>Atenciosamente, equipe Mente financeira</p>`+
                `<img src="cid:logoMente" alt="Logo mente financeira" style="width:150px; height: 150px;">`,
        attachments:[
            {
                filename:'logo-nova.png',
                path:'./public/logo-nova.png',
                cid:'logoMente'
            }
        ]        
    })
    .then(() => console.log('Email enviado com sucesso'))
    .catch((error) => console.log('Erro ao enviar email: ', error))
    
    res.send({message:'Token enviado com sucesso!'})
});