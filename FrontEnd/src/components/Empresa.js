import React from "react";
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Grid from '@material-ui/core/Grid';
import Autorenew from '@material-ui/icons/Autorenew';
import Link from '@material-ui/core/Link';
import NumberFormat from 'react-number-format';
import { useForm } from "react-hook-form";
import { ThemeProvider, createMuiTheme, } from "@material-ui/core";

const theme = createMuiTheme({
  palette: {
    type: "dark"
  }
});

var i = 0;
const empresas = ["Claro", "ETB", "Movistar"];
const descripciones = ["Pago a Claro", "Pago de noviembre", "Pago factura"];

export const transdata = {
  valor: 10000,
  descripcion: "Pago celular",
};

export const empresadata = {
  nombreEmpresa: "Claro",
  idEmpresa: 1010101010,
};

export default function Empresa() {

  const { handleSubmit } = useForm({});

  const onSubmit = persona => {
    i++;
    // eslint-disable-next-line
    if (i == 3) i = 0; 
    empresadata.nombreEmpresa = empresas[i];
    empresadata.idEmpresa = Math.floor(Math.random() * 9999999999 + 1000000000);
    transdata.valor = Math.floor(Math.random() * 100000 + 10000);
    transdata.descripcion = descripciones[i];
  }

  return (
    <>
      <ThemeProvider theme={theme}>
        <form onSubmit={handleSubmit(onSubmit)} className="form">
          <h2 className="h2" > Información de la transacción
          <Link component="button">
              <Autorenew />
            </Link>
          </h2>

          <div >
            <Grid container spacing={2}>
              <Grid item xs={6} md={3}>
                <div className="div1" >
                  <List >
                    <ListItem>
                      <ListItemText
                        primary="Empresa"
                        secondary={empresadata.nombreEmpresa}
                      />
                    </ListItem>
                  </List>
                </div>
              </Grid>

              <Grid item xs={6} md={3}>
                <div className="div1">
                  <List >
                    <ListItem>
                      <ListItemText
                        primary="NIT"
                        secondary={empresadata.idEmpresa}
                      />
                    </ListItem>
                  </List>
                </div>
              </Grid>

              <Grid item xs={6} md={3}>
                <div className="div1">
                  <List >
                    <ListItem>
                      <ListItemText
                        primary="Valor a pagar"
                        secondary={<NumberFormat value={transdata.valor} displayType={'text'} thousandSeparator={true} prefix={'$'} />}
                      />
                    </ListItem>
                  </List>
                </div>
              </Grid>

              <Grid item xs={6} md={3}>
                <div className="div1">
                  <List >
                    <ListItem>
                      <ListItemText
                        primary="Descripción"
                        secondary={transdata.descripcion}
                      />
                    </ListItem>
                  </List>
                </div>
              </Grid>

            </Grid>
          </div>
        </form>
      </ThemeProvider>
    </>
  );
}
