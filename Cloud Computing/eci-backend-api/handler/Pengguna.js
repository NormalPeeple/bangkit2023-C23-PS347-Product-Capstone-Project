import bodyParser from "body-parser";
import Pengguna from "../models/ModelPengguna.js"
import bcrypt from "bcryptjs"

const bodyParse = bodyParser.urlencoded({extends:true});

export const getPengguna = [bodyParse, async(req, res) => {
   try {
      const pengguna = await Pengguna.findAll()
      res.json(pengguna)
   } catch (error) {
      console.log(error)
   }
}]


export const Register = [bodyParse, async(req, res) => {
   const {penyelenggara, agama, kota_asal_penyelenggara, email, password, no_hp} = req.body
   // const salt = await bcrypt.genSalt(8)
   // const hashPassword = bcrypt.hash(password, salt, function(error, hash){
   //    if (error){
   //       console.log(error)
   //    } else {
   //       console.log(hashPassword)
   //    }
   // })
   try {
      await Pengguna.create({
         penyelenggara: penyelenggara,
         agama: agama,
         kota_asal_penyelenggara: kota_asal_penyelenggara,
         email: email,
         password: password,
         no_hp: no_hp
      })
      res.json({
         message: "Berhasil registrasi...!"
      })
   } catch (error) {
      console.log(error)
   }
}]

export const Login = [bodyParse, async(req, res) => {
   try {
      const pengguna = await Pengguna.findAll({
         where:{
            email: req.body.email
         }
      })
      // const match = await bcrypt.compare(req.body.password, pengguna[0].password)
      if(pengguna[0].password != req.body.password) return res.status(400).json({
            message: "Password atau username salah...!"
         })
      res.json({
         message: "Berhasil login...!"
      })
   } catch (error) {
      res.status(404).json({
         message: "Pengguna tidak ditemukan...!"
      })
   }
}]