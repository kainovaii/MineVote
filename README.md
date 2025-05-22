<p align="center">
    <br />
    <img src="https://i.postimg.cc/C1C5nLFR/favicon.png" width="30%">
    <br />
</p>
<p align="center">
    <img src="https://img.shields.io/badge/Version-0.2-orange.svg" />
    <img style="margin-left: 10px;" src="https://img.shields.io/badge/License-MIT-orange.svg" />
</p>

### TODO
  - ⌛ Customizable GUI (Glass color, size, text, currency)
  - ⌛ Ranking player
  - ⌛ Ranking multi page all voter
  - ⌛ Player has voted view
  - ⌛ DiscordSRV support
  - ⌛ Command give vote

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