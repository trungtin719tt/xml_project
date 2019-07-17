import pyodbc
# connection = pyodbc.connect('DRIVER={SQL Server};SERVER=TINTSE62434\TINNT;DATABASE=BookDB;UID=sa;PWD=1')
# how_many = connection.getinfo(pyodbc.SQL_MAX_CONCURRENT_ACTIVITIES)
# print(how_many)

def isEnglish(s):
    try:
        s.encode(encoding='utf-8').decode('ascii')
    except UnicodeDecodeError:
        return False
    else:
        return True
    
def isFloat(f):
    return isinstance(f, float)

def isInt(f):
    return isinstance(f, int)
    
def validateBook(id, name, author, score):
    if (not isEnglish(name) or not isEnglish(name) or not isFloat(score) or not isInt(id)):
        return False
    return True

def validateReview(reviewBy, reviewContent, score, bookID):
    if ((not isEnglish(reviewContent)) or reviewContent is None):
        return False
    if (reviewBy is None):
        return False
    if (score is None or not isFloat(score)):
        return False
    if (not isInt(bookID)):
        return False
    return True

def getClassification(score):
    if (score >= 4):
        return 1
    if (score >= 3):
        return 0
    return -1

def reformString(s):
    return s.replace('\'', '\'\'')


def main():
    cnxn = pyodbc.connect('DRIVER={SQL Server};SERVER=TINTSE62434\TINNT;DATABASE=BookDB;UID=sa;PWD=1')
    cursor = cnxn.cursor()


    count = True
    numcount = 0
    while count == True:
        count = False
        cursor.execute('EXEC GetNewCrawledBooks')
        rows = cursor.fetchall()
        for row in rows:
            numcount = numcount + 1
            print(numcount)
            count = True
            id = row[0]
            name = row[1]
            author = row[2]
            score = row[3]
            try:
                if(validateBook(id=id,name=name,author=author,score=score)):
                    cursor.execute('EXEC AddNewValidatedBook @pName = N\'' + name + '\', @pAuthor = N\'' + author + '\', @pScore = ' + str(score) + ', @pGetFromBookID = ' + str(id) + '')
                    cnxn.commit()
                    cursor.execute('EXEC GetReview @BookID = ' + str(id) + '')
                    reviews = cursor.fetchall()
                    for review in reviews:
                        bookID = review[6]
                        reviewBy = review[1]
                        reviewContent = reformString(review[2])
                        reviewScore = review[3]
                        numOfLike = review[4]
                        reviewID = review[0]
                        classification = getClassification(score=reviewScore)
                        if (numOfLike is None):
                            numOfLike = 0
                        try:
                            if (validateReview(reviewBy=reviewBy, reviewContent=reviewContent, score=reviewScore, bookID=bookID)):
                                cursor.execute('EXEC InsertReview @pGetFromBookID = ' + str(bookID) + ',	@pReviewBy = N\'' + reviewBy + '\', @pReviewContent = N\'' + reviewContent + '\', @pScore = ' + str(reviewScore) + ',	@pNumOfLike = ' + str(numOfLike) + ', @pClassification = ' + str(classification) + ', @pIsCrawled = true')
                                cnxn.commit()
                        except :
                            print('EXEC InsertReview @pGetFromBookID = ' + str(bookID) + ',	@pReviewBy = N\'' + reviewBy + '\', @pReviewContent = N\'' + reviewContent + '\', @pScore = ' + str(reviewScore) + ',	@pNumOfLike = ' + str(numOfLike) + ', @pClassification = ' + str(classification) + ', @pIsCrawled = true')
                            pass
                        
                        
            except:
                pass
            finally:
                cnxn.commit()
    print('done')
main()
        

    