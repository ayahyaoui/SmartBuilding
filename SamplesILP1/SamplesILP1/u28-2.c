#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp64; 
  ILP_Object ilptmp65; 
ilptmp64 = ILP_Integer2ILP(1); 
ilptmp65 = ILP_Integer2ILP(2); 

  {
    ILP_Object x1 = ilptmp64;
    ILP_Object y2 = ilptmp65;
{ 
  ILP_Object ilptmp66; 
ilptmp66 = ILP_Integer2ILP(3); 

  {
    ILP_Object y3 = ilptmp66;
{ 
  ILP_Object ilptmp67; 
  ILP_Object ilptmp68; 
ilptmp67 = x1; 
ilptmp68 = y3; 
return ILP_Plus(ilptmp67, ilptmp68);
} 

  }
}

  }
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
