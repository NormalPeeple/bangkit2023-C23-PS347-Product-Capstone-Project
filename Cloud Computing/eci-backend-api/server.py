import os
import flask
import tensorflow as tf


app = flask.Flask(__name__)
MODEL_PATH = 'PreEventModel.h5'
MODEL = tf.keras.models.load_model(MODEL_PATH)

THRESHOLD = 0.7

@app.route('/predict', methods=['POST'])
def use_model():
    """Return hasil prediksi dari model dengan model machine learning
        
    """
    req_json = flask.request.get_json()
    penyelenggara, jenis_event, cara_transaksi, kota_asal = req_json['penyelenggara'], req_json['jenis_event'], req_json['cara_transaksi'], req_json['kota_asal_penyelenggara']
    model_input = [tf.constant([penyelenggara]),tf.constant([jenis_event]), tf.constant([cara_transaksi]), tf.constant([kota_asal])]
    probability = MODEL.predict(model_input)

    if probability >= THRESHOLD:
        predict = 1
    else:
        predict = 0

    response = flask.make_response(flask.jsonify({
        "response_code" : 200,
        "isFraud" : predict,
        "probabilitas": str(probability.flatten()[0]),
        "data": req_json
    }))
    return response

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=int(os.environ.get("PORT", 8080)))
