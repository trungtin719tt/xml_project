import pandas as pd
import numpy as np
import pickle
from keras.preprocessing.text import Tokenizer
from keras.models import Sequential, load_model
from pathlib import Path


def main():
    # For reproducibility
    np.random.seed(1237)

    # These are the labels we stored from our training
    # The order is very important here.

    labels = np.array(['dislike', 'like', 'neutral'])

    # load our saved model
    model = load_model('test_classification_model.h5')

    # load tokenizer
    tokenizer = Tokenizer()
    with open('tokenizer.pickle', 'rb') as handle:
        tokenizer = pickle.load(handle)

    test_files = ["data/test/like/10290.txt",
                "data/test/neutral/10511.txt",
                "data/test/dislike/10285.txt"
                ]
    x_data = []
    for t_f in test_files:
        t_f_data = Path(t_f).read_text()
        x_data.append(t_f_data)

    x_data_series = pd.Series(x_data)
    x_tokenized = tokenizer.texts_to_matrix(x_data_series, mode='tfidf')

    i=0
    for x_t in x_tokenized:
        prediction = model.predict(np.array([x_t]))
        predicted_label = labels[np.argmax(prediction[0])]
        print("File ->", test_files[i], "Predicted label: " + predicted_label)
        i += 1



