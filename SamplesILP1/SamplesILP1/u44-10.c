#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 
ILP_Object print;


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp117; 
{ 
  ILP_Object ilptmp118; 
{ 
  ILP_Object ilptmp119; 
  ILP_Object ilptmp120; 
ilptmp119 =  ILP_String2ILP("a"); 
ilptmp120 =  ILP_String2ILP("b"); 
ilptmp118 = ILP_LessThan(ilptmp119, ilptmp120);
} 
ilptmp117 = ILP_print(ilptmp118);
}
{ 
  ILP_Object ilptmp121; 
{ 
  ILP_Object ilptmp122; 
  ILP_Object ilptmp123; 
ilptmp122 =  ILP_String2ILP("a"); 
ilptmp123 =  ILP_String2ILP("b"); 
ilptmp121 = ILP_LessThanOrEqual(ilptmp122, ilptmp123);
} 
ilptmp117 = ILP_print(ilptmp121);
}
{ 
  ILP_Object ilptmp124; 
{ 
  ILP_Object ilptmp125; 
  ILP_Object ilptmp126; 
ilptmp125 =  ILP_String2ILP("a"); 
ilptmp126 =  ILP_String2ILP("b"); 
ilptmp124 = ILP_GreaterThan(ilptmp125, ilptmp126);
} 
ilptmp117 = ILP_print(ilptmp124);
}
{ 
  ILP_Object ilptmp127; 
{ 
  ILP_Object ilptmp128; 
  ILP_Object ilptmp129; 
ilptmp128 =  ILP_String2ILP("a"); 
ilptmp129 =  ILP_String2ILP("b"); 
ilptmp127 = ILP_GreaterThanOrEqual(ilptmp128, ilptmp129);
} 
ilptmp117 = ILP_print(ilptmp127);
}
return ilptmp117; 
} 

} 

static ILP_Object ilp_caught_program () {
  struct ILP_catcher* current_catcher = ILP_current_catcher;
  struct ILP_catcher new_catcher;

  if ( 0 == setjmp(new_catcher._jmp_buf) ) {
    ILP_establish_catcher(&new_catcher);
    return ilp_program();
  };
  return ILP_current_exception;
}

int main (int argc, char *argv[]) 
{ 
  ILP_START_GC; 
  ILP_print(ilp_caught_program()); 
  ILP_newline(); 
  return EXIT_SUCCESS; 
} 
