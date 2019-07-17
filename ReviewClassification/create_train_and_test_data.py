import pyodbc
import shutil
import os

def main(trainPageSize):
    
    path = 'data/train'
    if os.path.exists(path):
        shutil.rmtree(path)
    os.makedirs(path)
    path = 'data/train/neutral'
    if os.path.exists(path):
        shutil.rmtree(path)
    os.makedirs(path)
    path = 'data/train/like'
    if os.path.exists(path):
        shutil.rmtree(path)
    os.makedirs(path)
    path = 'data/train/dislike'
    if os.path.exists(path):
        shutil.rmtree(path)
    os.makedirs(path)
    
    cnxn = pyodbc.connect('DRIVER={SQL Server};SERVER=TINTSE62434\TINNT;DATABASE=BookDB;UID=sa;PWD=1')
    cursor = cnxn.cursor()

    print("start writing train data")
    count = 0
    for x in range(1, trainPageSize):
        count = count + 1
        cursor.execute('EXEC GetValidatedReview @pageNum = '+ str(x) +'')
        rows = cursor.fetchall()
        for row in rows:
            id = row[0]
            classification = row[6]
            reviewContent = row[2]
            f = None;
            try:
                if (classification == 0):
                    f = open("data/train/neutral/"+ str(id) + ".txt", "w")
                    f.write(reviewContent)
                elif (classification == 1):
                    f = open("data/train/like/"+ str(id) + ".txt", "w")
                    f.write(reviewContent)
                else:
                    f = open("data/train/dislike/"+ str(id) + ".txt", "w")
                    f.write(reviewContent)
            except:
                print("error")
                pass
            finally:
                if (f is not None):
                    f.close()
    print("end writing train data")

    # count = 0
    # print("start writing test data")
    # for x in range(trainPageSize//2 + 1, trainPageSize):
    #     count = count + 1
    #     cursor.execute('EXEC GetValidatedReview @pageNum = '+ str(x) +'')
    #     rows = cursor.fetchall()
    #     for row in rows:
    #         id = row[0]
    #         classification = row[6]
    #         reviewContent = row[2]
    #         f = None;
    #         try:
    #             if (classification == 0):
    #                 f = open("data/test/neutral/"+ str(id) + ".txt", "w")
    #                 f.write(reviewContent)
    #             elif (classification == 1):
    #                 f = open("data/test/like/"+ str(id) + ".txt", "w")
    #                 f.write(reviewContent)
    #             else:
    #                 f = open("data/test/dislike/"+ str(id) + ".txt", "w")
    #                 f.write(reviewContent)
    #         except:
    #             print("error")
    #             pass
    #         finally:
    #             if (f is not None):
    #                 f.close()
    # print("end writing test data")
        