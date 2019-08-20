package sharora.mysubscription;

public class Member {
    String firstname;
    String lastname;
    String Startsub;
    String Endsub;
    String Totalamt;
    String pkgtype;
    String MACid;
    String paymentmethod;
    String paymentoption;
    String name_key;
    String phoneNumber;

    public Member(){

    }

    public Member(String name, String Lastname, String startsub, String endsub, String totalamt, String pkgtype, String macid,String paymentmethod,String paymentoption, String name_key, String PhoneNumber){

        this.firstname = name;
        this.lastname = Lastname;
        this.Startsub = startsub;
        this.Endsub = endsub;
        this.Totalamt = totalamt;
        this.pkgtype = pkgtype;
        this.MACid = macid;
        this.paymentmethod = paymentmethod;
        this.paymentoption = paymentoption;
        this.name_key = name_key;
        this.phoneNumber = PhoneNumber;
    }

    public String Getname(){
        return firstname;
    }

    public void Setname(String Name){
        this.firstname = Name;
    }
    public String Getlastname(){
        return lastname;
    }

    public void Setlastname(String lastname){
        this.lastname = lastname;
    }
    public String GetStartsub(){
        return Startsub;
    }

    public void SetStarsub(String startsub){
        this.Startsub = startsub;
    }

    public String GetEndsub(){
        return Endsub;
    }

    public void SetEndsub(String endsub){
        this.Endsub = endsub;
    }
    public String GetTotalamt(){
        return Totalamt;
    }

    public void SetToalamt(String totalamt){
        this.Totalamt = totalamt;
    }
    public String Getpkgtype(){
        return pkgtype;
    }

    public void Setpkgtype(String Pkgtype){
        this.pkgtype = Pkgtype;
    }
    public String GetMACid(){
        return MACid;
    }

    public void SetMACid(String macid){
        this.MACid = macid;
    }
    public String GetPaymentMethod(){
        return paymentmethod;
    }
    public void SetPaymentMethod(String Paymentmethod){
        this.paymentmethod = Paymentmethod;
    }
    public String GetPaymentOption(){
        return paymentoption;
    }
    public void SetPaymentOption(String PaymentOption){
        this.paymentoption = PaymentOption;
    }

    public String Getname_key(){
        return name_key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void Setname_key(String Namekey){
        this.name_key = Namekey;
    }
}