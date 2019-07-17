import create_train_and_test_data as ct
import continue_training as con

def main():
    ct.main(trainPageSize=100)
    con.main(modelName='test_classification_model.h5')
