import pandas as pd
import numpy as np
import pickle
from keras.preprocessing.text import Tokenizer
from keras.models import Sequential, load_model
from keras.layers import Activation, Dense, Dropout
from sklearn.preprocessing import LabelBinarizer
import sklearn.datasets as skds
from pathlib import Path


def main(modelName):
    print('continue training')
    # For reproducibility
    np.random.seed(1237)

    # Source file directory
    path_train = "data/train"

    files_train = skds.load_files(path_train,load_content=False)

    label_index = files_train.target
    label_names = files_train.target_names
    labelled_files = files_train.filenames

    data_tags = ["filename","category","review"]
    data_list = []

    # Read and add data from file to a list
    print("start reading data")
    i=0
    for f in labelled_files:
        data_list.append((f,label_names[label_index[i]],Path(f).read_text()))
        i += 1
    print('finish reading data')
    # We have training data available as dictionary filename, category, data
    data = pd.DataFrame.from_records(data_list, columns=data_tags)

    # 20 news groups
    num_labels = 3
    vocab_size = 15000
    batch_size = 100

    # lets take 80% data as training and remaining 20% for test.
    train_size = int(len(data) * .8)

    train_posts = data['review'][:train_size]
    train_tags = data['category'][:train_size]
    train_files_names = data['filename'][:train_size]

    test_posts = data['review'][train_size:]
    test_tags = data['category'][train_size:]
    test_files_names = data['filename'][train_size:]

    # define Tokenizer with Vocab Size
    tokenizer = Tokenizer(num_words=vocab_size)
    tokenizer.fit_on_texts(train_posts)

    x_train = tokenizer.texts_to_matrix(train_posts, mode='tfidf')
    x_test = tokenizer.texts_to_matrix(test_posts, mode='tfidf')

    encoder = LabelBinarizer()
    encoder.fit(train_tags)
    y_train = encoder.transform(train_tags)
    y_test = encoder.transform(test_tags)

    # load our saved model
    print('load existed model')
    model = load_model(modelName)

    history = model.fit(x_train, y_train,
                        batch_size=batch_size,
                        epochs=30,
                        verbose=1,
                        validation_split=0.1)

    # creates a HDF5 file 'my_model.h5'
    model.model.save('test_classification_model.h5')

    # Save Tokenizer i.e. Vocabulary
    with open('tokenizer.pickle', 'wb') as handle:
        pickle.dump(tokenizer, handle, protocol=pickle.HIGHEST_PROTOCOL)


    score = model.evaluate(x_test, y_test,
                        batch_size=batch_size, verbose=1)

    print('Test accuracy:', score[1])
    print('end training')

