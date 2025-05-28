<p align="center">
    <br />
    <img src="https://i.postimg.cc/C1C5nLFR/favicon.png" width="30%">
    <br />
</p>
<p align="center">
    <img src="https://img.shields.io/badge/Version-0.4-orange.svg" />
    <img style="margin-left: 10px;" src="https://img.shields.io/badge/License-MIT-orange.svg" />
</p>

### TODO
  - ⌛ Ranking player position
  - ⌛ Player has voted view

## 📥 Installation

1. **Download** the `MineVote.jar` file (link to be added).
2. **Place** the `.jar` file into your server’s `plugins/` folder.
3. **Make sure** the following plugins are also installed in the `plugins/` folder:
   - `PlaceholderAPI`
   - `NuVotifier`
4. **Start** or **restart** your server.
5. A `MineVote/` folder will be generated with the configuration file.

## ⚙️ NuVotifier Configuration

To ensure that votes are properly received by your server, configure NuVotifier:

1. Open the file:  
   `plugins/NuVotifier/config.yml`

2. Check or adjust the following fields:

```yaml
host: 0.0.0.0
port: 8192
token: "<automatically generated>"
```

## 🔧 Placeholders (PlaceholderAPI)

MineVote fournit plusieurs placeholders pour afficher des informations sur le boost de vote dans votre serveur, via PlaceholderAPI.

| Placeholder                 | Description                                    |
|-----------------------------|------------------------------------------------|
| `%minevote_counter%`         | Nombre total de votes comptabilisés pendant le boost. |
| `%minevote_objective%`       | Objectif de votes défini dans la configuration. |
| `%minevote_status%`          | Statut du boost : affiche un message indiquant si le boost est activé ou non. |
| `%minevote_timeleft%`        | Temps restant du boost, affiché au format mm:ss, ou une icône horloge si terminé. |

### Comment utiliser ?

1. Assurez-vous que PlaceholderAPI est installé et chargé sur votre serveur.
2. Intégrez ces placeholders dans vos messages, panneaux, ou commandes compatibles PlaceholderAPI.