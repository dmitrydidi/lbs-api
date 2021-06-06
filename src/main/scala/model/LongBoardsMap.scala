package model

object LongBoardsMap {

  case class LongBoard(id: String, mark: String, model: String, quantity: Int)
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
