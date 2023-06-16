import { Sequelize } from "sequelize";
import db from "../config/db.js";

const {DataTypes} = Sequelize

const Donasi = db.define('donasi', {
   jumlah_donasi:{
      type: DataTypes.INTEGER
   }
},{
   freezeTableName: true
})

export default Donasi