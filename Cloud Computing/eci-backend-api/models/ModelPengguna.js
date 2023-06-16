import { Sequelize } from "sequelize"
import db from "../config/db.js"

const {DataTypes} = Sequelize

const Pengguna = db.define('data_pengguna', {
   penyelenggara:{
      type: DataTypes.STRING
   },
   agama:{
      type: DataTypes.STRING
   },
   kota_asal_penyelenggara:{
      type: DataTypes.STRING
   },
   email:{
      type: DataTypes.STRING
   },
   password:{
      type: DataTypes.STRING
   },
   no_hp:{
      type: DataTypes.STRING
   }
},{
   freezeTableName:true
})

export default Pengguna
