#!/usr/bin/env groovy

import groovy.transform.ToString

Result result(Board board){
    if(board.anyLineFilledWith(Token.X) || board.anyColumnFilledWith(Token.X) || board.anyDiagonalFilledWith(Token.X)) return Result.XWins;
    if(board.anyLineFilledWith(Token.O) || board.anyColumnFilledWith(Token.O) || board.anyDiagonalFilledWith(Token.O)) return Result.OWins;
    if(board.notFilledYet()) return Result.NotOverYet;
    return Result.Draw;
}

enum Result{
    NotOverYet,
    Draw,
    XWins,
    OWins
}


enum Token{
    Empty,
    X,
    O
}

@ToString()
class Board{
    def content = [[Token.Empty] * 3] * 3

    def notFilledYet(){
        content.any{line ->
           line.any{cell ->
                cell == Token.Empty
            } 
        }
    }


    def anyLineFilledWith(token){
        return anyFilledWith(lines(content), token)
    }

    def anyColumnFilledWith(token){
        return anyFilledWith(columns(content), token)
    }

    def anyDiagonalFilledWith(token){
        return anyFilledWith(diagonals(content), token)
    }

    private def anyFilledWith(collection, token){
        collection.any { item ->
            item.every { cell -> cell == token }
        }
    }

    private def rotate(array){
        return array.transpose()
    }

    private def lines(array){
        return array
    }

    private def columns(array){
        return lines(rotate(array))
    }

    private def diagonals(array){
        def size = array.size() - 1
        def mainDiagonal = (0..size).collect().inject([]){ diagonal, index -> 
            diagonal + array[index][index]
        }

        def secondaryDiagonal = (0..size).collect().inject([]){ diagonal, index -> 
            diagonal + array[index][size - index]
        }

        return [mainDiagonal, secondaryDiagonal]
    }
}

def emptyBoard = new Board()
assert result(emptyBoard) == Result.NotOverYet
println "Result for board $emptyBoard is ${result(emptyBoard)}"

def unfinishedBoard = new Board(
    content: [
                [Token.X, Token.Empty, Token.O],
                [Token.O, Token.Empty, Token.X],
                [Token.Empty, Token.Empty, Token.Empty]
    ]
)

assert result(unfinishedBoard) == Result.NotOverYet
println "Result for board $unfinishedBoard is ${result(unfinishedBoard)}"

def xWonBoard = new Board(
    content: [
                [Token.O, Token.O, Token.X],
                [Token.O, Token.X, Token.Empty],
                [Token.X, Token.Empty, Token.Empty]
    ]
)

assert result(xWonBoard) == Result.XWins
println "Result for board $xWonBoard is ${result(xWonBoard)}"

def oWonBoard = new Board(
    content: [
                [Token.O, Token.Empty, Token.X],
                [Token.O, Token.X, Token.Empty],
                [Token.O, Token.Empty, Token.Empty]
    ]
)

assert result(oWonBoard) == Result.OWins
println "Result for board $oWonBoard is ${result(oWonBoard)}"

def drawBoard = new Board(
    content: [
                [Token.O, Token.X, Token.O],
                [Token.X, Token.O, Token.X],
                [Token.X, Token.O, Token.X]
    ]
)
assert result(drawBoard) == Result.Draw
println "Result for board $drawBoard is ${result(drawBoard)}"
