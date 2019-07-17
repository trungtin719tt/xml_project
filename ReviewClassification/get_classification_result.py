import pandas as pd
import numpy as np
import pickle
import pyodbc
from keras.preprocessing.text import Tokenizer
from keras.models import Sequential, load_model
from pathlib import Path

def isEnglish(s):
    try:
        s.encode(encoding='utf-8').decode('ascii')
    except UnicodeDecodeError:
        return False
    else:
        return True

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
    # connect database
    cnxn = pyodbc.connect('DRIVER={SQL Server};SERVER=TINTSE62434\TINNT;DATABASE=BookDB;UID=sa;PWD=1')
    cursor = cnxn.cursor()

    cursor.execute('EXEC GetNewReviews')
    rows = cursor.fetchall()
    
    reviews = {}
    i = 0
    for row in rows:
        id = row[0]
        content = row[2]
        if (not isEnglish(content)):
            cursor.execute('Update NewReview set IsNew = 0 WHERE ID = ' + str(id) + '')
            cnxn.commit()
            i = i + 1
            continue
        reviews[str(id)] = content
        i = i + 1
    for x, y in reviews.items():
        try:
            x_data = []
            x_data.append(y)
            x_data_series = pd.Series(x_data)
            x_tokenized = tokenizer.texts_to_matrix(x_data_series, mode='tfidf')
            prediction = model.predict(np.array([x_tokenized[0]]))
            predicted_label = labels[np.argmax(prediction[0])]
            result = 0
            if (predicted_label == 'like'):
                result = 1
            elif (predicted_label == 'dislike'):
                result = -1
                
            cursor.execute('Update NewReview set IsNew = 0, Classification = ' + str(result) + ' WHERE ID = ' + x + '')
            cnxn.commit()
        except:
            cursor.execute('Update NewReview set IsNew = 0 WHERE ID = ' + x + '')
            cnxn.commit()
            
        


