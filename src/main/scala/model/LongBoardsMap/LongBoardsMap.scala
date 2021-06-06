package model.LongBoardsMap

import model.LongBoard.LongBoard

object LongBoardsMap {
  // POST
  case class Add(longBoard: LongBoard)
  // GET
  case object GetAll
  case class GetByMark(mark: String)
  case class GetByModel(model: String)
  case class GetById(id: String)
  // PUT
  case class Edit(id: String)
  // DELETE
  case class Delete(id: String)

}
