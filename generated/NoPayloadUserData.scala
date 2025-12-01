package fxprof

class NoPayloadUserData private (args: NoPayloadUserDataArgs) {
  def `type`: NoPayloadUserData_Type.type = args.`type`
  def innerWindowID: Option[Double] = args.innerWindowID

  def `withtype`(value: NoPayloadUserData_Type.type): NoPayloadUserData =
    copy(_.copy(`type` = value))
  
  def withInnerWindowID(value: Option[Double]): NoPayloadUserData =
    copy(_.copy(innerWindowID = value))
  

  private def copy(f: NoPayloadUserDataArgs => NoPayloadUserDataArgs) = 
    new NoPayloadUserData(f(args))
  
}

object NoPayloadUserData {
  def apply(
    `type`: NoPayloadUserData_Type.type,
  ): NoPayloadUserData = 
    new NoPayloadUserData(NoPayloadUserDataArgs(
      `type` = `type`,
    ))
}
private[fxprof] case class NoPayloadUserDataArgs(
  `type`: NoPayloadUserData_Type.type,
  innerWindowID: Option[Double] = None,
)
