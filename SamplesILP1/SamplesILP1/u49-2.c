#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 
ILP_Object to_string;


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp175; 
  ILP_Object ilptmp176; 
{ 
  ILP_Object ilptmp177; 
{ 
  ILP_Object ilptmp178; 
  ILP_Object ilptmp179; 
ilptmp178 = ILP_Integer2ILP(1); 
ilptmp179 = ILP_Integer2ILP(0); 
ilptmp177 = ILP_GreaterThanOrEqual(ilptmp178, ilptmp179);
} 
ilptmp175 = ILP_to_string(ilptmp177);
}
ilptmp176 =  ILP_String2ILP("*"); 
return ILP_Plus(ilptmp175, ilptmp176);
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
